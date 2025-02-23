package com.circuit_breaker.address_service.service;

import com.circuit_breaker.address_service.model.Address;

public interface AddressService {

    Address getAddressByPostalCode(String postalCode);

}
