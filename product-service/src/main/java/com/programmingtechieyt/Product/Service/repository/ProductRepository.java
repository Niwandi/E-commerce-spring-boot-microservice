package com.programmingtechieyt.Product.Service.repository;

import com.programmingtechieyt.Product.Service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
