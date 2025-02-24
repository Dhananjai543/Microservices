package com.retry_mechanism.order_service.model;

import lombok.Data;

@Data
public class Success implements Type {
    private final String msg;
}
