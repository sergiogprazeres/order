package com.ava.api.order.domain.ports.service;

import java.util.List;
import java.util.Optional;
import com.ava.api.order.domain.dto.Order;

public interface OrderService {
    String createOrder();
    Optional<Order> updateOrder(String id);
    Optional<Order> consultOrder(String id);
    List<Order> consultAllOrders();
}

