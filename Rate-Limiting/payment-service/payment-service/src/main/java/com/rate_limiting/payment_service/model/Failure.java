package com.rate_limiting.payment_service.model;

import lombok.Data;

@Data
public class Failure implements Type {
    private final String msg;
}
