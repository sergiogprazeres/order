package com.ava.api.order.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ava.api.order.domain.dto.Order;
import com.ava.api.order.domain.enums.BusinessErrorEnum;
import com.ava.api.order.domain.ports.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;

	@Mock
	private OrderService orderService;
	
	@Mock
	private Logger logger;

	@Test
	@DisplayName(value = "1. getAllOrders() with success and return empty List")
	void getAllOrdersSuccessEmpty() {
		ResponseEntity<Object> responseExpected = new ResponseEntity<Object>(new ArrayList<Order>(), HttpStatus.OK);
		when(orderService.consultAllOrders()).thenReturn(new ArrayList<Order>());

		ResponseEntity<Object> response = orderController.getAllOrders();

		assertEquals(responseExpected.getBody(), response.getBody(), "The expected result should match the actual result and it to be a empty array.");
		assertEquals(response.getStatusCode().value(), HttpStatus.OK.value(), "The expected return value is 200.");
	}

	@Test
	@DisplayName(value = "2. getAllOrders() with success and return a List")
	void getAllOrdersSuccess() {
		ResponseEntity<Object> responseExpected = new ResponseEntity<Object>(generateResponseGetAllOrders(), HttpStatus.OK);
		when(orderService.consultAllOrders()).thenReturn(generateResponseGetAllOrders());

		ResponseEntity<Object> response = orderController.getAllOrders();

		assertEquals(responseExpected.getBody(), response.getBody(), "The expected result should match the actual result and it to be a list array.");
		assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName(value = "3. getAllOrders() with exception.")
	void getAllOrdersError() {
		ResponseEntity<Object> responseExpected = new ResponseEntity<>(BusinessErrorEnum.CONSULT_ERROR.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		when(orderService.consultAllOrders()).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = orderController.getAllOrders();

		assertEquals(responseExpected.getBody(), response.getBody(), "The expected result should be an error.");
		assertEquals(response.getStatusCode().value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}


	@Test
	@DisplayName(value = "4. getOrderById() with success and return a Order")
	void getOrderByIdSuccess() {
		ResponseEntity<Order> responseExpected = new ResponseEntity<Order>(generateResponseGetOrderById(), HttpStatus.OK);
		Optional<Order> optionalOrder = Optional.of(generateResponseGetOrderById());
		when(orderService.consultOrder(anyString())).thenReturn(optionalOrder);

		ResponseEntity<Order> response = orderController.getOrderById("dac26d3b-1bf8-4b0d-9e52-4110cb41e40b");

		assertEquals(responseExpected.getBody(), response.getBody(), "The expected result should match the actual result and it to be a empty array.");
		assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
	}

	@Test
	@DisplayName(value = "5. getOrderById() without find a Order")
	void getOrderByIdError() {
		Optional<Order> optionalOrder = Optional.empty();
		when(orderService.consultOrder(anyString())).thenReturn(optionalOrder);

		ResponseEntity<Order> response = orderController.getOrderById("dac26d3b-1bf8-4b0d-9e52-4110cb41e40b");

		assertEquals(response.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
	}

	
	@Test
	@DisplayName(value = "6. createOrder() with success and return a id")
	void createOrderSuccess() {
		ResponseEntity<String> responseExpected = new ResponseEntity<String>("7aa7b6ee-6750-4ff1-af92-90d6584d2591", HttpStatus.OK);
		when(orderService.createOrder()).thenReturn("7aa7b6ee-6750-4ff1-af92-90d6584d2591");

		ResponseEntity<String> response = orderController.createOrder();

		assertEquals(responseExpected.getBody(), response.getBody(), "The expected result should match the actual result and it to be a empty array.");
		assertEquals(response.getStatusCode().value(), HttpStatus.CREATED.value());
	}
	
	@Test
	@DisplayName(value = "7. createOrder() without success and return an error message")
	void createOrderError() {
		ResponseEntity<String> responseExpected = new ResponseEntity<String>(BusinessErrorEnum.SAVE_ERROR.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		when(orderService.createOrder()).thenThrow(RuntimeException.class);

		ResponseEntity<String> response = orderController.createOrder();

		assertEquals(responseExpected.getBody(), response.getBody(), "The expected result should match the actual result and it to be a empty array.");
		assertEquals(response.getStatusCode().value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
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

