package com.shopping.startup.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariationValueRequest {

    private String id;
    private String variationValueName;
    private String variationId;
}
