package com.ava.api.order.infra.adapters.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ava.api.order.domain.ports.consumer.Consumer;
import com.ava.api.order.domain.ports.service.OrderService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsumerKafka implements Consumer {
	private final Logger logger = LoggerFactory.getLogger(ConsumerKafka.class);
	
	@Autowired
	private OrderService orderService;
	
	@Override
	@KafkaListener(topics = "${topic.neworder}", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(String message) throws IOException {
		logger.info("====  Consumer Kafka - Consumed menssage: {} ====", message);
		orderService.updateOrder(message);
	}
}

