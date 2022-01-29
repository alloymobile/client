package com.alloymobile.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public PageableHandlerMethodArgumentResolver pageableResolver() {
        return new PageableHandlerMethodArgumentResolver();
    }
}
