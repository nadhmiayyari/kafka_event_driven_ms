package com.app.handler;


import com.app.core.ProductCreatedEvent;
import com.app.exceptions.NotRetryableException;
import com.app.exceptions.RetryableException;
import com.app.model.ProcessedEventEntity;
import com.app.repository.ProcessedEventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Component
//groupId="product-created-event")
@KafkaListener(topics = {"products-event-topic"})
@Transactional
public class ProductCreatedEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ProcessedEventRepository processedEventRepository;

    private final RestTemplate restTemplate;

    public ProductCreatedEventHandler(ProcessedEventRepository processedEventRepository, RestTemplate restTemplate) {
        this.processedEventRepository = processedEventRepository;
        this.restTemplate = restTemplate;
    }
    // provide the topic name here
    // for handling events from different classes here , we can defio,e the kafkaListener Annotation above the class name
    // each method needs to be annotated with the @KafkaHandler annotation

    //message id and message key are read from the message headers , and to tell kafka handfler that productCreatedEvbent will be a payload
    // we can use the @payload annotation therefore

    //required is true by default
    @KafkaHandler
    public void handle(@Payload ProductCreatedEvent productCreatedEvent,
                       @Header(value = "messageId", required = false) String messageId,
                       @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        // if(true) throw new NotRetryableException("an error took place , no need to conusme this message again . ");
        LOGGER.info("received a new event :" + productCreatedEvent.getTitle());
        LOGGER.info("received a new event :" + productCreatedEvent.getProductId());

        String url = "http://localhost:8088/response/200";
        ProcessedEventEntity entity = processedEventRepository.findProcessedEventEntityByMessageId(messageId);
        if(entity!=null){
            LOGGER.info("found a duplicate message id : {}",entity.getMessageId());
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (response.getStatusCode().value() == HttpStatus.OK.value()) {
                LOGGER.info("Received response from a remote service " + response.getBody());
            }
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            throw new RetryableException(ex.getMessage());
        } catch (HttpServerErrorException ex) {
            LOGGER.error(ex.getMessage());
            throw new NotRetryableException(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new NotRetryableException(ex.getMessage());
        }

        try {
            processedEventRepository.save(new ProcessedEventEntity(messageId, productCreatedEvent.getProductId()));
        } catch (DataIntegrityViolationException ex) {
            throw new NotRetryableException(ex);
        }
    }

}
