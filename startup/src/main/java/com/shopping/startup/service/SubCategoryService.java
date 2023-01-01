package com.shopping.startup.service;


import com.shopping.startup.entity.Category;
import com.shopping.startup.entity.SubCategory;
import com.shopping.startup.model.SubCategoryRequest;
import com.shopping.startup.repository.CategoryRepository;
import com.shopping.startup.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;


    public List<SubCategory> getAllSubCategories() {

        return subCategoryRepository.findAll();
    }

    public SubCategory getSubCategory(String id) {
        if(id != null && id.strip().length() > 0){
            Optional<SubCategory> subCategory = subCategoryRepository.findById(Long.valueOf(id.strip()));
            if(subCategory.isPresent())
                return subCategory.get();
        }
        return null;
    }

    public SubCategory addSubCategory(SubCategoryRequest subCategoryRequest) {
        if(subCategoryRequest.getCategoryId() == null || subCategoryRequest.getCategoryId().strip().length() == 0)
            return null;

        if((subCategoryRequest.getSubCategoryName() != null && subCategoryRequest.getSubCategoryName().strip().length() != 0)
                && (subCategoryRequest.getSubCategoryImage() != null && subCategoryRequest.getSubCategoryImage().strip().length() != 0)) {
            Optional<Category> category = categoryRepository.findById(Long.valueOf(subCategoryRequest.getCategoryId().strip()));
            if(category.isPresent()){
                SubCategory subCategory = SubCategory.builder()
                        .subCategoryName(subCategoryRequest.getSubCategoryName().strip())
                        .subCategoryImage(subCategoryRequest.getSubCategoryImage().strip())
                        .category(category.get())
                        .build();
                return subCategoryRepository.save(subCategory);
            }
        }
        return null;
    }

    @Transactional
    public SubCategory updateSubCategory(SubCategoryRequest subCategoryRequest) {
        if(subCategoryRequest.getId() == null || subCategoryRequest.getId().strip().length() == 0)
            return null;

        Optional<SubCategory> subCategory = subCategoryRepository.findById(Long.valueOf(subCategoryRequest.getId().strip()));
        if(subCategory.isEmpty())
            return null;
        if(subCategoryRequest.getCategoryId() == null || subCategoryRequest.getCategoryId().strip().length() == 0)
            return null;

        if((subCategoryRequest.getSubCategoryName() != null && subCategoryRequest.getSubCategoryName().strip().length() != 0)
                && (subCategoryRequest.getSubCategoryImage() != null && subCategoryRequest.getSubCategoryImage().strip().length() != 0)) {
            Optional<Category> category = categoryRepository.findById(Long.valueOf(subCategoryRequest.getCategoryId().strip()));
            if(category.isPresent()){
                SubCategory updatedSubCategory = subCategory.get();
                updatedSubCategory.setSubCategoryImage(subCategoryRequest.getSubCategoryImage().strip());
                updatedSubCategory.setSubCategoryName(subCategoryRequest.getSubCategoryName().strip());
                updatedSubCategory.setCategory(category.get());

                return subCategoryRepository.save(updatedSubCategory);
            }
        }
        return null;
    }

    @Transactional
    public SubCategory deleteSubCategory(SubCategoryRequest subCategoryRequest) {
        if(subCategoryRequest.getId() != null && subCategoryRequest.getId().strip().length() > 0) {
            subCategoryRepository.deleteById(Long.valueOf(subCategoryRequest.getId().strip()));
            return SubCategory.builder().build();
        }
        return null;
    }
}
