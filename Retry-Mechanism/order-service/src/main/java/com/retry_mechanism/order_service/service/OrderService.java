package com.retry_mechanism.order_service.service;

import com.retry_mechanism.order_service.model.Type;

public interface OrderService {
    Type getOrderByOrderNumber(String orderNumber);
}
