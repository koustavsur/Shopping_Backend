package com.shopping.startup.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationRequest {

    private String id;
    private String productQty;
    private String price;
    private String image;
    private String productId;
    private String variationValueId;

}
