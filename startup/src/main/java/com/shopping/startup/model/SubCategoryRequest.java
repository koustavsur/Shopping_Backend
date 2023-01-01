package com.shopping.startup.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryRequest {

    private String id;
    private String subCategoryName;
    private String subCategoryImage;
    private String categoryId;
}
