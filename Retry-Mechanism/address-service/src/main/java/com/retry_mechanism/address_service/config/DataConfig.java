package com.retry_mechanism.address_service.config;

import com.retry_mechanism.address_service.model.Address;
import com.retry_mechanism.address_service.repository.AddressRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataConfig {

    @Autowired
    private AddressRepository addressRepository;

    @PostConstruct
    public void setupSampleData() {
        addressRepository.saveAll(Arrays.asList(
                Address.builder().state("Haryana").postalCode("135106").city("Yamunanagar").build(),
                Address.builder().state("Maharashtra").postalCode("411036").city("Pune").build(),
                Address.builder().state("Punjab").postalCode("101123").city("Chandigarh").build(),
                Address.builder().state("West Bengal").postalCode("617227").city("Calcutta").build()
        ));
    }

}
