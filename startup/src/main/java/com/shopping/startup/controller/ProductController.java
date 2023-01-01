package com.shopping.startup.controller;


import com.shopping.startup.entity.Product;
import com.shopping.startup.model.ProductRequest;
import com.shopping.startup.service.ProductService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final Validation validation;

    @GetMapping("/allProducts")
    public ResponseEntity<?> getAllProducts(){
        try{
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e){
            log.info("Error while getting all products", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(value = "id") String id){
        try{
            Product product = productService.getProduct(id);
            if(product != null) {
                return ResponseEntity.ok(product);
            }
            return ResponseEntity.status(404).body(Constants.RESOURCE_NOT_FOUND);
        } catch (Exception e) {
            log.info("Error while getting product by id", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {
        try{
            if(validation.isAdmin()){
                Product product = productService.addProduct(productRequest);
                if(product != null){
                    return ResponseEntity.ok(product);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while adding product", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest) {
        try{
            if(validation.isAdmin()){
                Product product = productService.updateProduct(productRequest);
                if(product != null){
                    return ResponseEntity.ok(product);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while updating product", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestBody ProductRequest productRequest) {
        try{
            if(validation.isAdmin()){
                Product product = productService.deleteProduct(productRequest);
                if(product != null){
                    return ResponseEntity.ok(product);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while deleting product", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
