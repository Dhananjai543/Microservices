package com.retry_mechanism.address_service.controller;

import com.retry_mechanism.address_service.model.Address;
import com.retry_mechanism.address_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.SocketTimeoutException;

@RestController
@RequestMapping("addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{postalCode}")
    public Address getAddressByPostalCode(@PathVariable("postalCode") String postalCode) throws SocketTimeoutException {
        return addressService.getAddressByPostalCode(postalCode);
    }

}
