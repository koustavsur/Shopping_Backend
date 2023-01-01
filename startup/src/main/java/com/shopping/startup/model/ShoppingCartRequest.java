package com.shopping.startup.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartRequest {

    private String id;
    private String userEmail;
    private String productVariationId;
    private String quantity;
}
