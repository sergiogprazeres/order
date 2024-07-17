package com.ava.api.order.infra.adapters.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ava.api.order.domain.ports.producer.Messenger;

@Service
public class MessengerKafka implements Messenger{
	private static final Logger logger = LoggerFactory.getLogger(MessengerKafka.class);
	
	@Value("${topic.neworder}")
	private String topicNewOrder;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void sendMessage(String message) {
		logger.info("kafkaTemplate: {}", kafkaTemplate);
		logger.info("send message to kafka: {}", message);
		this.kafkaTemplate.send(topicNewOrder, message);
	}
}