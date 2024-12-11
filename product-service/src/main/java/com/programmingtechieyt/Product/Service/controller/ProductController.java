package com.programmingtechieyt.Product.Service.controller;

import com.programmingtechieyt.Product.Service.dto.ProductRequest;
import com.programmingtechieyt.Product.Service.dto.ProductResponse;
import com.programmingtechieyt.Product.Service.model.Product;
import com.programmingtechieyt.Product.Service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private  final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts(); // Step 2: Call the service layer to get all products
    }

}
