package com.ava.api.order.infra.adapters.producer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class MessengerKafkaTest {
    @InjectMocks
    private MessengerKafka messengerKafka;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Mock
	private Logger logger;
    
    @Test
    void testSendMessage() {
        String sampleMessage = "abc";

        messengerKafka.sendMessage(sampleMessage);

        verify(kafkaTemplate).send(any(), eq(sampleMessage));
    }
}
