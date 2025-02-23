package com.rate_limiting.payment_service.service;

import com.rate_limiting.payment_service.model.Type;

public interface PaymentService {
    Type submitPayment(String paymentInfo);
}
