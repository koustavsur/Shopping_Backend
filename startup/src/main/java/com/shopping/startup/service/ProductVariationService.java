package com.shopping.startup.service;


import com.shopping.startup.entity.Product;
import com.shopping.startup.entity.ProductVariation;
import com.shopping.startup.entity.VariationValue;
import com.shopping.startup.model.ProductVariationRequest;
import com.shopping.startup.repository.ProductRepository;
import com.shopping.startup.repository.ProductVariationRepository;
import com.shopping.startup.repository.VariationValueRepository;
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
public class ProductVariationService {

    private final ProductVariationRepository productVariationRepository;
    private final ProductRepository productRepository;
    private final VariationValueRepository variationValueRepository;
    private final Validation validation;


    public List<ProductVariation> getAllProductVariations() {

        return productVariationRepository.findAll();
    }


    public ProductVariation getProductVariation(String id) {
        if(!validation.isFieldValid(id))
            return null;

        Optional<ProductVariation> productVariation = productVariationRepository.findById(Long.valueOf(id.strip()));
        if(productVariation.isEmpty())
            return null;

        return productVariation.get();
    }

    public ProductVariation addProductVariation(ProductVariationRequest productVariationRequest) {
        if(!isValidRequest(productVariationRequest))
            return null;

        Optional<Product> product = productRepository.findById(Long.valueOf(productVariationRequest.getProductId().strip()));
        if(product.isEmpty())
            return null;

        Optional<VariationValue> variationValue = variationValueRepository.findById(Long.valueOf(productVariationRequest.getVariationValueId().strip()));
        if(variationValue.isEmpty())
            return null;

        ProductVariation productVariation = ProductVariation.builder()
                .productPrice(Float.valueOf(productVariationRequest.getPrice().strip()))
                .productQty(Integer.valueOf(productVariationRequest.getProductQty().strip()))
                .productVariationImage(productVariationRequest.getImage())
                .product(product.get())
                .variationValue(variationValue.get())
                .build();

        return productVariationRepository.save(productVariation);
    }

    private boolean isValidRequest(ProductVariationRequest productVariationRequest){
        return validation.isFieldValid(productVariationRequest.getProductQty()) && validation.isFieldValid(productVariationRequest.getPrice())
                && validation.isFieldValid(productVariationRequest.getProductId()) && validation.isFieldValid(productVariationRequest.getVariationValueId());
    }

    @Transactional
    public ProductVariation updateProductVariation(ProductVariationRequest productVariationRequest) {
        if(!(isValidRequest(productVariationRequest) && validation.isFieldValid(productVariationRequest.getId().strip())))
            return null;

        Optional<ProductVariation> productVariation = productVariationRepository.findById(Long.valueOf(productVariationRequest.getId().strip()));
        if(productVariation.isEmpty())
            return null;

        Optional<Product> product = productRepository.findById(Long.valueOf(productVariationRequest.getProductId().strip()));
        if(product.isEmpty())
            return null;

        Optional<VariationValue> variationValue = variationValueRepository.findById(Long.valueOf(productVariationRequest.getVariationValueId().strip()));
        if(variationValue.isEmpty())
            return null;

        ProductVariation updatedProductVariation = productVariation.get();
        updatedProductVariation.setProduct(product.get());
        updatedProductVariation.setProductVariationImage(productVariationRequest.getImage());
        updatedProductVariation.setVariationValue(variationValue.get());
        updatedProductVariation.setProductQty(Integer.valueOf(productVariationRequest.getProductQty().strip()));
        updatedProductVariation.setProductPrice(Float.valueOf(productVariationRequest.getPrice().strip()));

        return productVariationRepository.save(updatedProductVariation);

    }


    @Transactional
    public ProductVariation deleteProductVariation(ProductVariationRequest productVariationRequest) {
        if(validation.isFieldValid(productVariationRequest.getId())){
            productVariationRepository.deleteById(Long.valueOf(productVariationRequest.getId().strip()));
            return ProductVariation.builder().build();
        }
        return null;
    }
}
