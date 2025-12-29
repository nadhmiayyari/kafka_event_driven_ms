package com.app.product_microservice;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    //configs(Map.of("min.insync.replicas","2"))
    // number of replicas and ack so that the operation is considered successfull


    @Bean
    NewTopic createTopic(){
        return TopicBuilder
                .name("products-event-topic")
                .partitions(3)
                .build();
    }
}
