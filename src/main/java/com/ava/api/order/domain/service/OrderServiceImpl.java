package com.ava.api.order.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.api.order.domain.ports.producer.Messenger;
import com.ava.api.order.domain.dto.Order;
import com.ava.api.order.domain.enums.StatusOrderEnum;
import com.ava.api.order.domain.ports.repository.OrderRepository;
import com.ava.api.order.domain.ports.service.OrderService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private Messenger messenger;

    /**
     * @return
     */
    @Override
    public String createOrder() {
        logger.info("LOG - OrderServiceImpl - createOrder");
        Order order = new Order(UUID.randomUUID().toString(),StatusOrderEnum.AGUARDANDO_ENVIO.name());
        order = orderRepository.save(order);
        messenger.sendMessage(order.getId());
        return order.getId();
    }


    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Order> updateOrder(String id) {
        logger.info("LOG - OrderServiceImpl - updateOrder");
        Optional<Order> optionalOrder = consultOrder(id);
        Order order = null;
        if (optionalOrder.isPresent()) {
        	order = optionalOrder.get();
            order.setStatus(StatusOrderEnum.ENVIADO_TRANSPORTADORA.name());
            order = orderRepository.save(order);
        }
        return Optional.of(order);
    }

    /**
     * @param id 
     * @return 
     */
    @Override
    public Optional<Order> consultOrder(String id) {
        logger.info("LOG - OrderServiceImpl - consultOrder");
        return orderRepository
                .findById(id);
    }

    /**
     * @return
     */
    @Override
    public List<Order> consultAllOrders() {
        logger.info("LOG - OrderServiceImpl - consultAllOrders");
        return orderRepository
                .findAll();
    }
}

