package com.ava.api.order.infra.adapters.consumer;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import com.ava.api.order.domain.ports.service.OrderService;

@ExtendWith(MockitoExtension.class)
class ConsumerKafkaTest {

	@InjectMocks
	private ConsumerKafka consumerKafka;

	@Mock
	private OrderService orderService;
    
	@Mock
	private Logger logger;

	@Test
    void testConsume() throws IOException {
        String sampleMessage = "123";

        consumerKafka.consume(sampleMessage);

        verify(orderService).updateOrder(sampleMessage);
    }
	
}