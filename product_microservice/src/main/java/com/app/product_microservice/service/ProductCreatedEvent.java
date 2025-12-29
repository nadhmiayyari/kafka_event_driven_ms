package com.app.product_microservice.service;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreatedEvent {

    private String title;
    private BigDecimal price;
    private Integer quantity;
    private String productId;



}
