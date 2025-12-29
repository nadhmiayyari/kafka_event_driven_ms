package com.app.product_microservice.service;

import com.app.product_microservice.dto.CreateProductRequestDto;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org. slf4j. Logger;


@Service
public class ProductServiceImpl implements ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }


    //to make this code synchronous ==> there is one hint , future.join() ==> addition makes

    @Override
    public String createProduct(CreateProductRequestDto dto) {
        String productId = UUID.randomUUID().toString();
        ProductCreatedEvent productCreatedEvent = ProductCreatedEvent
                .builder()
                .productId(productId)
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .title(dto.getTitle())
                .build();
       CompletableFuture<SendResult<String,ProductCreatedEvent>> future =  kafkaTemplate.send("products-event-topic",productId,productCreatedEvent);
        future.whenComplete((result,exception)->{
            if(exception !=null) {
                LOGGER.error("Failed to send message : "+exception.getMessage());
            }else{
                LOGGER.info("Message sent successfully: "+result.getRecordMetadata());
            }
        });

        // the thread will wait until this line until the completable future completes
        future.join();
        return productId;
    }
}
