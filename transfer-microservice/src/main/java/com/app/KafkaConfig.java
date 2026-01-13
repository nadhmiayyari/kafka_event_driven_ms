package com.app;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Value("withdraw-money-topic")
	private String withdrawTopicName;

	@Value("deposit-money-topic")
	private String depositTopicName;

	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.producer.key-serializer}")
	private String keySerializer;

	@Value("${spring.kafka.producer.value-serializer}")
	private String valueSerializer;

	@Value("${spring.kafka.producer.acks}")
	private String acks;

	@Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
	private String deliveryTimeout;

	@Value("${spring.kafka.producer.properties.linger.ms}")
	private String linger;

	@Value("${spring.kafka.producer.properties.request.timeout.ms}")
	private String requestTimeout;

	@Value("${spring.kafka.producer.properties.enable.idempotence}")
	private boolean idempotence;

	@Value("${spring.kafka.producer.properties.max.in.flight.requests.per.connection}")
	private int inflightRequests;

	@Value("${spring.kafka.producer.transaction-id-prefix}")
	private String transactionPrefix;

	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
		props.put(ProducerConfig.ACKS_CONFIG, acks);
		props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
		props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
		props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);

		props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, idempotence);
		props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, inflightRequests);
		props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionPrefix);

		return props;
	}

	@Bean
	ProducerFactory<String, Object> producerFactory() {
		DefaultKafkaProducerFactory<String, Object> factory = new DefaultKafkaProducerFactory<>(producerConfigs());
		factory.setTransactionIdPrefix(transactionPrefix);
		return factory;
 	}


	//for kafka template we can set new Kafka Template using producerFactory bean
	@Bean
	KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}


	@Bean
	KafkaTransactionManager<String,Object> kafkaTransactionManager(ProducerFactory<String, Object> producerFactory){
		return new KafkaTransactionManager<>(producerFactory);
	}

	@Bean
	NewTopic createWithdrawTopic() {
		LOGGER.info("Creating withdraw topic: " + withdrawTopicName);
		return TopicBuilder.name(withdrawTopicName).partitions(3).replicas(1).build();
	}

	@Bean
	NewTopic createDepositTopic() {
		LOGGER.info("Creating deposit topic: " + depositTopicName);

		return TopicBuilder.name(depositTopicName).partitions(3).replicas(1).build();
	}
}
