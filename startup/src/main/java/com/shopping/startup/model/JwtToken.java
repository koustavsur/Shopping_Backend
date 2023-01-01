package com.shopping.startup.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class JwtToken {

    private String jwtToken;
}
