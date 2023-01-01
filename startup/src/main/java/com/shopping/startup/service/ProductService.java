package com.shopping.startup.service;


import com.shopping.startup.entity.Product;
import com.shopping.startup.entity.SubCategory;
import com.shopping.startup.model.ProductRequest;
import com.shopping.startup.repository.ProductRepository;
import com.shopping.startup.repository.SubCategoryRepository;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final Validation validation;


    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public Product getProduct(String id) {
        if(!validation.isFieldValid(id))
            return null;

        Optional<Product> product = productRepository.findById(Long.valueOf(id.strip()));
        if(product.isEmpty())
            return null;

        return product.get();
    }


    public Product addProduct(ProductRequest productRequest) {

        if(!isValidRequest(productRequest))
            return null;

        Optional<SubCategory> subCategory = subCategoryRepository.findById(Long.valueOf(productRequest.getSubCategoryId().strip()));
        if(subCategory.isEmpty())
            return null;

        Product product = Product.builder()
                .productName(productRequest.getProductName().strip())
                .productDetails(productRequest.getProductDetails().strip())
                .productImage(productRequest.getProductImage().strip())
                .subCategory(subCategory.get())
                .build();

        return productRepository.save(product);

    }

    private boolean isValidRequest(ProductRequest request){
        return validation.isFieldValid(request.getProductDetails()) && validation.isFieldValid(request.getProductName())
                && validation.isFieldValid(request.getProductImage()) && validation.isFieldValid(request.getSubCategoryId());
    }

    @Transactional
    public Product updateProduct(ProductRequest productRequest) {

        if(!(isValidRequest(productRequest) && validation.isFieldValid(productRequest.getId())))
            return null;

        Optional<Product> product = productRepository.findById(Long.valueOf(productRequest.getId().strip()));
        if(product.isEmpty())
            return null;

        Optional<SubCategory> subCategory = subCategoryRepository.findById(Long.valueOf(productRequest.getSubCategoryId().strip()));
        if(subCategory.isEmpty())
            return null;

        Product updatedProduct = product.get();
        updatedProduct.setProductName(productRequest.getProductName().strip());
        updatedProduct.setProductDetails(productRequest.getProductDetails().strip());
        updatedProduct.setProductImage(productRequest.getProductImage().strip());
        updatedProduct.setSubCategory(subCategory.get());

        return productRepository.save(updatedProduct);
    }


    @Transactional
    public Product deleteProduct(ProductRequest productRequest) {
        if(validation.isFieldValid(productRequest.getId())){
            productRepository.deleteById(Long.valueOf(productRequest.getId().strip()));
            return Product.builder().build();
        }
        return null;
    }
}
