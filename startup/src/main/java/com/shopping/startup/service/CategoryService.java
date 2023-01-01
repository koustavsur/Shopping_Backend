package com.shopping.startup.service;


import com.shopping.startup.entity.Category;
import com.shopping.startup.model.CategoryRequest;
import com.shopping.startup.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(String id) {
        if(id != null && id.strip().length() > 0){
             Optional<Category> category = categoryRepository.findById(Long.valueOf(id.strip()));
             if(category.isPresent())
                 return category.get();
        }
        return null;
    }

    public Category addCategory(CategoryRequest categoryRequest) {
        if(categoryRequest.getCategoryName() != null && categoryRequest.getCategoryName().strip().length() != 0) {
            Category category = Category.builder()
                    .categoryName(categoryRequest.getCategoryName().strip())
                    .categoryImage(categoryRequest.getCategoryImage().strip())
                    .build();
            return categoryRepository.save(category);
        }
        return null;
    }


    @Transactional
    public Category updateCategory(CategoryRequest categoryRequest) {
        if(categoryRequest.getId() != null && categoryRequest.getId().strip().length() > 0) {
            if(categoryRequest.getCategoryName() != null && categoryRequest.getCategoryName().strip().length() != 0){
                Optional<Category> category = categoryRepository.findById(Long.valueOf(categoryRequest.getId().strip()));
                if(category.isPresent()){
                    Category updatedCategory = category.get();
                    updatedCategory.setCategoryName(categoryRequest.getCategoryName().strip());
                    updatedCategory.setCategoryImage(categoryRequest.getCategoryImage().strip());

                    return categoryRepository.save(updatedCategory);
                }
            }
        }
        return null;
    }

    @Transactional
    public Category deleteCategory(CategoryRequest categoryRequest) {
        if(categoryRequest.getId() != null && categoryRequest.getId().strip().length() > 0) {
            categoryRepository.deleteById(Long.valueOf(categoryRequest.getId().strip()));
            return Category.builder().build();
        }
        return null;
    }
}
