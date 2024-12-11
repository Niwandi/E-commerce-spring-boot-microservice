package com.programmingTechieYt.Order_Service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//@Entity
//@Table(name = "t_orders")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//    private String orderNumber;
//    @OneToMany
//    private List <OrderLineItem> orderLineItemList;
//
//    public Order(String orderNumber, List<OrderLineItem> orderLineItemList) {
//        this.orderNumber = orderNumber;
//        this.orderLineItemList = orderLineItemList;
//    }
//}


@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String orderNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineItem> orderLineItemList;

    public Order(String orderNumber, List<OrderLineItem> orderLineItemList) {
        this.orderNumber = orderNumber;
        this.orderLineItemList = orderLineItemList;
    }
}

