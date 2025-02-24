package com.retry_mechanism.address_service.service;

import com.retry_mechanism.address_service.model.Address;
import com.retry_mechanism.address_service.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;
    @Override
    public Address getAddressByPostalCode(String postalCode) {
        return addressRepository.findByPostalCode(postalCode).orElseThrow(() -> new RuntimeException("Address not found: " + postalCode));
    }
}
