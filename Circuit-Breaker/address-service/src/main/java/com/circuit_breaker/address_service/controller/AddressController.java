package com.circuit_breaker.address_service.controller;

import com.circuit_breaker.address_service.model.Address;
import com.circuit_breaker.address_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{postalCode}")
    public Address getAddressByPostalCode(@PathVariable("postalCode") String postalCode){
        return addressService.getAddressByPostalCode(postalCode);
    }

}
