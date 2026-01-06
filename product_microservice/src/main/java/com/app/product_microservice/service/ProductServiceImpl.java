package com.app.product_microservice.service;

import com.app.core.ProductCreatedEvent;
import com.app.product_microservice.dto.CreateProductRequestDto;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org. slf4j.Logger;


@Service
public class ProductServiceImpl implements ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }


    //to make this code synchronous ==> there is one hint , future.join() ==> addition makes

    @Override
    public String createProduct(CreateProductRequestDto dto) throws ExecutionException, InterruptedException {
        String productId = UUID.randomUUID().toString();
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(dto.getTitle(),dto.getPrice(),
                productId,dto.getQuantity() );

       /*CompletableFuture<SendResult<String,ProductCreatedEvent>> future =  kafkaTemplate.send("products-event-topic",productId,productCreatedEvent);
        future.whenComplete((result,exception)->{
            if(exception !=null) {
                LOGGER.error("Failed to send message : "+exception.getMessage());
            }else{
                LOGGER.info("Message sent successfully: "+result.getRecordMetadata());
            }
        });

        // the thread will wait until this line until the completable future completes
        future.join();*/


        //to make this method wait until it receives ack from kafka broker
        //call a get method on it
        //the main advantage from sending messages synchronously
        // , is that we can wait for ACK from kafka brokers that the message is successfully stored in kafka topic
        ProducerRecord<String,ProductCreatedEvent> record =  new ProducerRecord<>("products-event-topic",productId,productCreatedEvent);
        record.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        SendResult<String,ProductCreatedEvent> result =
                    kafkaTemplate.send(record).get();

            LOGGER.info("partition"+result.getRecordMetadata().partition());
            LOGGER.info("topic"+result.getRecordMetadata().topic());
            LOGGER.info("offset"+result.getRecordMetadata().offset());

        return productId;
    }
}
