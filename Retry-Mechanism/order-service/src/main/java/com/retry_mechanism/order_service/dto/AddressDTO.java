package com.retry_mechanism.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    //no id generation code here. It is just a DTO
    private Integer id;

    private String postalCode;

    private String state;

    private String city;
}
