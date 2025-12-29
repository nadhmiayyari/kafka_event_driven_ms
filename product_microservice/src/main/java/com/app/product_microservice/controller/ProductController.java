package com.app.product_microservice.controller;


import com.app.product_microservice.dto.CreateProductRequestDto;
import com.app.product_microservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRequestDto dto){
        String productId = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
