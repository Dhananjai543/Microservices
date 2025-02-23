package com.rate_limiting.payment_service.service;

import com.rate_limiting.payment_service.model.Failure;
import com.rate_limiting.payment_service.model.Success;
import com.rate_limiting.payment_service.model.Type;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;


@Service
public class PaymentServiceImpl implements PaymentService{

    private final RestTemplate restTemplate;

    public PaymentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String SERVICE_NAME = "payment-service";
    private static final String PAYMENT_PROCESSOR_URL = "http://localhost:1010/api/v1/processor-payment";

//    Where is SERVICE_NAME Used?
//    It is defined as: SERVICE_NAME = "payment-service";
//    This must match the name in your rate-limiting properties:
//    resilience4j.ratelimiter.instances.payment-service.limit-for-period=5
    @Override
    @RateLimiter(name = SERVICE_NAME, fallbackMethod = "fallbackMethod")
    public Type submitPayment(String paymentInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(paymentInfo, headers);
        ResponseEntity<String> response = restTemplate.exchange(PAYMENT_PROCESSOR_URL, HttpMethod.POST, entity, String.class);
        Success success = new Success(response.getBody());
        return success;
    }

    private Type fallbackMethod(RequestNotPermitted requestNotPermitted){
        return new Failure("Payment service does not permit further calls");
    }
}
