package com.circuit_breaker.order_service.service;


import com.circuit_breaker.order_service.dto.AddressDTO;
import com.circuit_breaker.order_service.model.Failure;
import com.circuit_breaker.order_service.model.Order;
import com.circuit_breaker.order_service.model.Type;
import com.circuit_breaker.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String SERVICE_NAME = "order-service";

    private static final String ADDRESS_SERVICE_URL = "http://localhost:9090/addresses/";

    @Override
    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallbackMethod")
    public Type getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new RuntimeException("Order not found: " + orderNumber));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AddressDTO> httpEntity = new HttpEntity<AddressDTO>(null,httpHeaders);
        ResponseEntity<AddressDTO> responseEntity = restTemplate.exchange(
                ADDRESS_SERVICE_URL + order.getPostalCode(), HttpMethod.GET, httpEntity, AddressDTO.class);
        AddressDTO addressDTO = responseEntity.getBody();
        if (addressDTO != null){
            order.setShippingCity(addressDTO.getCity());
            order.setShippingState(addressDTO.getState());
            order.setPostalCode(addressDTO.getPostalCode());
        }

        return order;
    }

    private Type fallbackMethod(Exception e){
        return new Failure("Address service is not responding properly");
    }
}
