package com.circuit_breaker.order_service.service;

import com.circuit_breaker.order_service.model.Type;

public interface OrderService {
    Type getOrderByOrderNumber(String orderNumber);
}
