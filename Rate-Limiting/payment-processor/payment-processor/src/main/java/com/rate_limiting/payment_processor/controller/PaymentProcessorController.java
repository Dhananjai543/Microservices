package com.rate_limiting.payment_processor.controller;

import com.rate_limiting.payment_processor.service.PaymentProcessorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/processor-payment")
public class PaymentProcessorController {

    private final PaymentProcessorService paymentProcessorService;

    public PaymentProcessorController(PaymentProcessorService paymentProcessorService){
        this.paymentProcessorService = paymentProcessorService;
    }

    @PostMapping
    public String processPayment(@RequestBody String paymentInfo){
        return paymentProcessorService.processPayment(paymentInfo);
    }
}
