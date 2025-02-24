package com.circuit_breaker.order_service.service;


import com.circuit_breaker.order_service.dto.AddressDTO;
import com.circuit_breaker.order_service.model.Failure;
import com.circuit_breaker.order_service.model.Order;
import com.circuit_breaker.order_service.model.Type;
import com.circuit_breaker.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.stream.Stream;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String SERVICE_NAME = "order-service";

    private static final String ADDRESS_SERVICE_URL = "http://localhost:9090/addresses/";

    @Override
    @Retry(name = SERVICE_NAME, fallbackMethod = "fallbackMethod")
    public Type getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new RuntimeException("Order not found: " + orderNumber));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AddressDTO> httpEntity = new HttpEntity<AddressDTO>(null,httpHeaders);
        try {
            ResponseEntity<AddressDTO> responseEntity = restTemplate.exchange(
                    ADDRESS_SERVICE_URL + order.getPostalCode(),
                    HttpMethod.GET,
                    httpEntity,
                    AddressDTO.class);
            Stream.ofNullable(responseEntity.getBody()).forEach(it -> {
                order.setShippingCity(it.getCity());
                order.setShippingCity(it.getCity());
            });
        } catch (HttpServerErrorException e){
            System.out.println("Retry due to http server error at: " + Instant.now());
            throw e;
        } catch (ResourceAccessException e){
            System.out.println("Retry due to resource access error at: " + Instant.now());
            throw e;
        }

        return order;
    }

    private Type fallbackMethod(Exception e){
        return new Failure("Address service is not responding properly");
    }
}
