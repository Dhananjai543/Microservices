package com.circuit_breaker.order_service.model;

import lombok.Data;

@Data
public class Failure implements Type {
    private final String msg;
}
