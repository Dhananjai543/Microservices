package com.retry_mechanism.address_service.service;

import com.retry_mechanism.address_service.model.Address;

public interface AddressService {

    Address getAddressByPostalCode(String postalCode);

}
