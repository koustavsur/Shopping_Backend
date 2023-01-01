package com.shopping.startup.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    private String email;

    private String name;

    private String imageUrl;

    private String loginSource;

    private String role;
}
