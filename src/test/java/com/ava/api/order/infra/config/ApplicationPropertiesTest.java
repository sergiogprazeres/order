package com.ava.api.order.infra.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ava.api.order.OrderApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrderApplication.class)
@TestPropertySource("classpath:databaseproperties-test.properties")
public class ApplicationPropertiesTest {

    @Autowired
    private ApplicationProperties applicationProperties;

//    @Test
    public void whenSimplePropertyQueriedThenReturnsPropertyValue() 
      throws Exception {
        assertEquals( 
          "dev", applicationProperties.getEnviroment());
        assertEquals( 
          "order", applicationProperties.getName());
    }
    
}
