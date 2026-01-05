package com.app.core;


import java.math.BigDecimal;



public class ProductCreatedEvent {

    private String title;
    private BigDecimal price;
    private Integer quantity;
    private String productId;

    public ProductCreatedEvent( ) {
    }
    public ProductCreatedEvent(String title, BigDecimal price, String productId, Integer quantity) {
        this.title = title;
        this.price = price;
        this.productId = productId;
        this.quantity = quantity;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
