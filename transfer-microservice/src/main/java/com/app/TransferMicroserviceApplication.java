package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableKafka
public class TransferMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferMicroserviceApplication.class, args);
	}
	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
