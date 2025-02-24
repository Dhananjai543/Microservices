package com.retry_mechanism.order_service.model;

import lombok.Data;

@Data
public class Failure implements Type {
    private final String msg;
}
