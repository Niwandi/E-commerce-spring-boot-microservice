package com.programmingtechieyt.Product.Service.service;

import com.programmingtechieyt.Product.Service.dto.ProductRequest;
import com.programmingtechieyt.Product.Service.dto.ProductResponse;
import com.programmingtechieyt.Product.Service.model.Product;
import com.programmingtechieyt.Product.Service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private  final ProductRepository productRepository;//used to interact with the database

    public void createProduct(ProductRequest productRequest) {//responsible for creating a new product
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} created", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products= productRepository.findAll();// Step 3: Retrieve all products from the database
        return products.stream()//Step 4: Convert each Product entity to a ProductResponse DTO
                .map(this::mapToProductResponse)//Step 5: map each product into a productResponse object
                .toList(); // Step 6: Collect the results into a list and return
    }

    private ProductResponse mapToProductResponse(Product product) { //Step 4a: converts a Product entity to a ProductResponse DTO.
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
