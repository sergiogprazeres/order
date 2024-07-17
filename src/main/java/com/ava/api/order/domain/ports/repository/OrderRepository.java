package com.ava.api.order.domain.ports.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ava.api.order.domain.dto.Order;

public interface OrderRepository extends MongoRepository<Order, String>{
    Order save(Order order);
    Optional<Order> findById(String id);
    List<Order> findAll();
}