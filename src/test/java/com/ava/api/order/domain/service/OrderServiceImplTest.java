package com.ava.api.order.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import com.ava.api.order.domain.dto.Order;
import com.ava.api.order.domain.enums.StatusOrderEnum;
import com.ava.api.order.domain.ports.producer.Messenger;
import com.ava.api.order.domain.ports.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	@Mock
    private OrderRepository orderRepository;
    
	@Mock
    private Messenger messenger;
	
	@Mock
	private Logger logger;

	@Test
	@DisplayName(value = "1. consultAllOrders() with success and return empty List")
	void consultAllOrdersSuccessEmpty() {
		List<Order> ordersExpected = new ArrayList<Order>();
		when(orderRepository.findAll()).thenReturn(new ArrayList<Order>());

		List<Order> response = orderServiceImpl.consultAllOrders();

		assertEquals(ordersExpected, response, "The expected result should match the actual result and it to be a empty array.");
	}

	@Test
	@DisplayName(value = "2. consultAllOrders() with success and return a List")
	void consultAllOrdersSuccess() {
		List<Order> ordersExpected = generateResponseGetAllOrders();
		when(orderRepository.findAll()).thenReturn(generateResponseGetAllOrders());

		List<Order> response = orderServiceImpl.consultAllOrders();

		assertEquals(ordersExpected, response, "The expected result should match the actual result and it to be a list array.");
	}
	
	@Test
	@DisplayName(value = "4. consultOrder() with success and return a Order")
	void getOrderByIdSuccess() {
		Optional<Order> responseExpected = Optional.of(generateResponseGetOrderById());
		Optional<Order> optionalOrder = Optional.of(generateResponseGetOrderById());
		when(orderRepository.findById(anyString())).thenReturn(optionalOrder);

		Optional<Order> response = orderServiceImpl.consultOrder("dac26d3b-1bf8-4b0d-9e52-4110cb41e40b");

		assertEquals(responseExpected, response, "The expected result should match the actual result and it to be a empty array.");
	}

	@Test
	@DisplayName(value = "5. consultOrder() without find a Order")
	void getOrderByIdError() {
		Optional<Order> responseExpected = Optional.empty();
		when(orderRepository.findById(anyString())).thenReturn(responseExpected);

		Optional<Order> response = orderServiceImpl.consultOrder("dac26d3b-1bf8-4b0d-9e52-4110cb41e40b");

		assertEquals(response, Optional.empty());
	}

	
	@Test
	@DisplayName(value = "6. createOrder() with success and return a id")
	void createOrderSuccess() {
		String responseExpected = "dac26d3b-1bf8-4b0d-9e52-4110cb41e40b";
		when(orderRepository.save(any())).thenReturn(generateResponseGetOrderById());

		String response = orderServiceImpl.createOrder();

		assertEquals(responseExpected, response, "The expected result should match the response  and it to be a empty array.");
	}
	
	@Test
	@DisplayName(value = "7. createOrder() without success and return an error message")
	void createOrderError() {
		Order orderSent = generateResponseGetOrderById();
		orderSent.setStatus(StatusOrderEnum.ENVIADO_TRANSPORTADORA.name());
		//Optional<Order> responseSent = Optional.of(orderSent);
		when(orderRepository.findById(anyString())).thenReturn(Optional.of(generateResponseGetOrderById()));
		when(orderRepository.save(any())).thenReturn(orderSent);

		Optional<Order> response = orderServiceImpl.updateOrder("dac26d3b-1bf8-4b0d-9e52-4110cb41e40b");

		assertEquals(response.get().getStatus(), StatusOrderEnum.ENVIADO_TRANSPORTADORA.name());
	}

	private List<Order> generateResponseGetAllOrders() {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Order> responseList = null;
		try {
			responseList = objectMapper.readValue(new File("src/test/resources/mocks/ResponseGetAllOrdersList.json"), new TypeReference<List<Order>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return responseList;
	}

	private Order generateResponseGetOrderById() {
		ObjectMapper objectMapper = new ObjectMapper();
		Order order = null;
		try {
			order = objectMapper.readValue(new File("src/test/resources/mocks/ResponseGetOrderById.json"), Order.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return order;
	}
}

