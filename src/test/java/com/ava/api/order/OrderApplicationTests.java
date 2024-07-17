package com.ava.api.order;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderApplicationTests {
	
	@Autowired
    private OrderApplication orderApplication;

	//@Test
	void contextLoads() {
        assertNotNull(orderApplication);
	}

}
