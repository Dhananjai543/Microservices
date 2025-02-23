package com.rate_limiting.payment_processor.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentProcessorServiceImpl implements PaymentProcessorService {
    @Override
    public String processPayment(String paymentInfo) {
        return "Payment processed: " + paymentInfo;
    }
}
