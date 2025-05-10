package com.codeAsh.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.codeAsh.product_service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    // Custom query methods can be defined here if needed

}
