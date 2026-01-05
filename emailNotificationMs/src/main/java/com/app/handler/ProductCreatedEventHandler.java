package com.app.handler;


 import com.app.core.ProductCreatedEvent;
 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@KafkaListener(topics ={"products-event-topic"})
public class ProductCreatedEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // provide the topic name here
    // for handling events from different classes here , we can defio,e the kafkaListener Annotation above the class name
    // each method needs to be annotated with the @KJafkaHandler annotation

    @KafkaHandler
    public void handle(ProductCreatedEvent productCreatedEvent){
        LOGGER.info("received a new event :" + productCreatedEvent.getTitle());
        }

}
