package com.ava.api.order.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ava.api.order.domain.dto.Order;
import com.ava.api.order.domain.enums.BusinessErrorEnum;
import com.ava.api.order.domain.ports.service.OrderService;

import lombok.AllArgsConstructor;

@Controller
@RestController
@RequestMapping("/micro-order/v1/orders")
@AllArgsConstructor
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	

	@GetMapping
	public ResponseEntity<Object> getAllOrders() {
		try {
			List<Order> orders = orderService.consultAllOrders();
			return new ResponseEntity<>(orders, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(BusinessErrorEnum.CONSULT_ERROR.getErrorMessage().concat(": {}"), e.getMessage());
			return new ResponseEntity<>(BusinessErrorEnum.CONSULT_ERROR.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable String id) {
		return orderService.consultOrder(id).map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<String> createOrder() {
		try {
			String idSavedOrder = orderService.createOrder();
			return new ResponseEntity<>(idSavedOrder, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error(BusinessErrorEnum.SAVE_ERROR.getErrorMessage().concat(": {}"), e.getMessage());
			return new ResponseEntity<>(BusinessErrorEnum.SAVE_ERROR.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
