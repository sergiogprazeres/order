package com.ava.api.order.infra.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ava.api.order.app.controller.OrderController;
import com.ava.api.order.domain.ports.producer.Messenger;
import com.ava.api.order.domain.ports.repository.OrderRepository;
import com.ava.api.order.domain.ports.service.OrderService;
import com.ava.api.order.domain.service.OrderServiceImpl;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class BeanConfiguration implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(BeanConfiguration.class);

    @Value("${spring.data.mongodb.uri}")
    private String uri;
    
    @Bean
    OrderController orderController(OrderService orderService) {
    	logger.info("LOG - BeanConfiguration - orderController");
        return new OrderController(orderService);
    }
    
    @Bean
    OrderService orderService(OrderRepository orderRepository, Messenger messenger) {
    	logger.info("LOG - BeanConfiguration - orderService");
        return new OrderServiceImpl(orderRepository, messenger);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter converter) {
    	logger.info("LOG - BeanConfiguration - mongoTemplate");
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        return mongoTemplate;
    }

    @Bean
    public MongoClient mongoClient() {
        logger.info("LOG - MongoDBConfiguration - mongoClient");
         ConnectionString connectionString = new ConnectionString(uri);
         MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                 .applyConnectionString(connectionString)
                 .build();
 
         return MongoClients.create(mongoClientSettings);
     }
        
}