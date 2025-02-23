package com.rate_limiting.payment_service.controller;

import com.rate_limiting.payment_service.model.Type;
import com.rate_limiting.payment_service.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment-service")
public class PaymentServiceController {

    private final PaymentService paymentService;

    public PaymentServiceController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    public Type submitPayment(@RequestParam String paymentInfo){
        return paymentService.submitPayment(paymentInfo);
    }
}
