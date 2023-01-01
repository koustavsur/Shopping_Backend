package com.shopping.startup.controller;

import com.shopping.startup.entity.ProductVariation;
import com.shopping.startup.model.ProductVariationRequest;
import com.shopping.startup.service.ProductVariationService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productVariation")
@Slf4j
public class ProductVariationController {

    private final ProductVariationService productVariationService;
    private final Validation validation;

    @GetMapping("/allProductVariations")
    public ResponseEntity<?> getAllProductVariations(){
        try{
            return ResponseEntity.ok(productVariationService.getAllProductVariations());
        } catch (Exception e){
            log.info("Error while getting all product Variations", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductVariation(@PathVariable(value = "id") String id){
        try{
            ProductVariation productVariation = productVariationService.getProductVariation(id);
            if(productVariation != null) {
                return ResponseEntity.ok(productVariation);
            }
            return ResponseEntity.status(404).body(Constants.RESOURCE_NOT_FOUND);
        } catch (Exception e) {
            log.info("Error while getting product Variation by id", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/addProductVariation")
    public ResponseEntity<?> addProductVariation(@RequestBody ProductVariationRequest productVariationRequest) {
        try{
            if(validation.isAdmin()){
                ProductVariation productVariation = productVariationService.addProductVariation(productVariationRequest);
                if(productVariation != null){
                    return ResponseEntity.ok(productVariation);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while adding product variation", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PutMapping("/updateProductVariation")
    public ResponseEntity<?> updateProductVariation(@RequestBody ProductVariationRequest productVariationRequest) {
        try{
            if(validation.isAdmin()){
                ProductVariation productVariation = productVariationService.updateProductVariation(productVariationRequest);
                if(productVariation != null){
                    return ResponseEntity.ok(productVariation);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while updating product variation", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("/deleteProductVariation")
    public ResponseEntity<?> deleteProductVariation(@RequestBody ProductVariationRequest productVariationRequest) {
        try{
            if(validation.isAdmin()){
                ProductVariation productVariation = productVariationService.deleteProductVariation(productVariationRequest);
                if(productVariation != null){
                    return ResponseEntity.ok(productVariation);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while deleting product variation", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
