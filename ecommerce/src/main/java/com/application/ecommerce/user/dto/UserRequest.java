package com.application.ecommerce.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String address;
    private String city;
    private String state;
    private Integer postalCode;
    private String country;
}
