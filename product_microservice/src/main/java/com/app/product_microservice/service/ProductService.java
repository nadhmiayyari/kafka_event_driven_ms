package com.app.product_microservice.service;

import com.app.product_microservice.dto.CreateProductRequestDto;

public interface ProductService {

    String createProduct(CreateProductRequestDto dto) throws Exception;

}
