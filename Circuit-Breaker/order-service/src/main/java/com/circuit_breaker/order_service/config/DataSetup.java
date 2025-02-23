package com.circuit_breaker.order_service.config;

import com.circuit_breaker.order_service.model.Order;
import com.circuit_breaker.order_service.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataSetup {

    @Autowired
    private OrderRepository orderRepository;
    @PostConstruct
    public void setupData() {
        orderRepository.saveAll(Arrays.asList(
                Order.builder().id(1).orderNumber("0c70c0c2").postalCode("135106").build(),
                Order.builder().id(2).orderNumber("7f8f9f15").postalCode("411036").build(),
                Order.builder().id(3).orderNumber("394627b2").postalCode("101123").build(),
                Order.builder().id(4).orderNumber("283627cl").postalCode("617227").build()));

    }

}
