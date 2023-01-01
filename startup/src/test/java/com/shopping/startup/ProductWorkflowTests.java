package com.shopping.startup;


import com.shopping.startup.entity.Category;
import com.shopping.startup.entity.SubCategory;
import com.shopping.startup.entity.Variation;
import com.shopping.startup.entity.VariationValue;
import com.shopping.startup.repository.CategoryRepository;
import com.shopping.startup.repository.SubCategoryRepository;
import com.shopping.startup.repository.VariationRepository;
import com.shopping.startup.repository.VariationValueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
//@EnableJpaAuditing
public class ProductWorkflowTests {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private VariationRepository variationRepository;
    @Autowired
    private VariationValueRepository variationValueRepository;

    @Test
    public void testCategory(){

      /*  Category category = Category.builder()
                .categoryName("Jute")
                .categoryImage("Image")
                .build();

        categoryRepository.save(category);*/

        Category cat = categoryRepository.findByCategoryName("Jute");
        cat.setCategoryImage("http://updated-again");
        cat = categoryRepository.save(cat);

        SubCategory subCategory = SubCategory.builder()
                .subCategoryName("Planter")
                .subCategoryImage("Images")
                .category(cat)
                .build();
        subCategoryRepository.save(subCategory);

        List<SubCategory> subCategoryList = cat.getSubCategories();
        Assert.isTrue(subCategoryList.size() == 3);

    }

    @Test
    public void testVariations(){

        Variation variation = Variation.builder()
                .variationName("Colour")
                .build();
        variation = variationRepository.save(variation);

        VariationValue variationValue = VariationValue.builder()
                .variationValueName("Red")
                .variation(variation)
                .build();

        variationValueRepository.save(variationValue);
    }
}
