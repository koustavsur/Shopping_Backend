package com.shopping.startup.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderRequest {

    private String id;
    private String orderStatus;
    private String paymentMethod;
    private String orderTotal;
    private String userEmail;
    private String addressId;
}
