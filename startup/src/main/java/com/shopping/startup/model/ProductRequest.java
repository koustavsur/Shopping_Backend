package com.shopping.startup.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String id;
    private String productName;
    private String productImage;
    private String productDetails;
    private String subCategoryId;

}
