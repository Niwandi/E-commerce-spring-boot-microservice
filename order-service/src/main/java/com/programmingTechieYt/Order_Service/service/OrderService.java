package com.programmingTechieYt.Order_Service.service;

import com.programmingTechieYt.Order_Service.dto.InventoryResponse;
import com.programmingTechieYt.Order_Service.dto.OrderLineItemsDto;
import com.programmingTechieYt.Order_Service.dto.OrderRequest;
import com.programmingTechieYt.Order_Service.event.OrderPlacedEvent;
import com.programmingTechieYt.Order_Service.model.Order;
import com.programmingTechieYt.Order_Service.model.OrderLineItem;
import com.programmingTechieYt.Order_Service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItem = orderRequest.getOrderLineItemsDtoList() // Fetches the list of OrderLineItemsDto from the OrderRequest.
                .stream()
                .map(this::mapToEntity)
                .toList(); // Converts each OrderLineItemsDto to OrderLineItem using the mapToEntity method and collects them into a list.

        order.setOrderLineItemList(orderLineItem); // Sets the list of OrderLineItem instances to the Order.

        List<String> skuCodes = order.getOrderLineItemList().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();

        //log.info("Placing order: {}", order);
        //log.info("Checking inventory for SKUs: {}", skuCodes);

        // Call Inventory service, and place order if product is in stock
        InventoryResponse[] inventoryResponsarray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder ->
                        uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class) // Specify the type of response we are returning from the inventory service
                .block(); // Make this as synchronous request

        log.info("Inventory response: {}", (Object) inventoryResponsarray);

        boolean allProductsInStock = Arrays.stream(inventoryResponsarray)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            return "Order placed successfully";
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    private OrderLineItem mapToEntity(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemsDto.getPrice());
        orderLineItem.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItem;
    }
}
