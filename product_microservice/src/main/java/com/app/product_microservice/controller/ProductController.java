package com.app.product_microservice.controller;


import com.app.product_microservice.dto.CreateProductRequestDto;
import com.app.product_microservice.dto.ErrorMessage;
import com.app.product_microservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody CreateProductRequestDto dto){
        String productId = null;
        try {
            productId = productService.createProduct(dto);
        } catch (Exception e) {
           LOGGER.error(e.getMessage(),e);

           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                   ErrorMessage.builder()
                           .message(e.getMessage())
                           .timestamp(new Date())
                           .details("/products")
                           .build()
           );
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
