package com.circuit_breaker.order_service.controller;

import com.circuit_breaker.order_service.model.Type;
import com.circuit_breaker.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderServiceController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    private Type getOrderByOrderNumber(String orderNumber){
        return orderService.getOrderByOrderNumber(orderNumber);
    }

}
