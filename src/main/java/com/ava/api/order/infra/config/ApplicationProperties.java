package com.ava.api.order.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "spring.application")
@Getter
@Setter
public class ApplicationProperties {
    private String name;
    private String enviroment;
}

