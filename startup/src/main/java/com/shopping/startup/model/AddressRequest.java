package com.shopping.startup.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private String id;
    private String streetOne;
    private String streetTwo;
    private String city;
    private String state;
    private String country;
    private String pin;
    private String phone;
    private String email;

}
