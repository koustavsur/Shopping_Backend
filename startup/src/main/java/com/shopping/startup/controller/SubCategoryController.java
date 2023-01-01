package com.shopping.startup.controller;


import com.shopping.startup.entity.Category;
import com.shopping.startup.entity.SubCategory;
import com.shopping.startup.model.CategoryRequest;
import com.shopping.startup.model.SubCategoryRequest;
import com.shopping.startup.service.SubCategoryService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subCategory")
@Slf4j
public class SubCategoryController {

    private final SubCategoryService subCategoryService;
    private final Validation validation;

    @GetMapping("/allSubCategories")
    public ResponseEntity<?> getAllSubCategories(){
        try{
            return ResponseEntity.ok(subCategoryService.getAllSubCategories());
        } catch (Exception e){
            log.info("Error while getting all sub categories", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubCategory(@PathVariable(value = "id") String id){
        try{
            SubCategory subCategory = subCategoryService.getSubCategory(id);
            if(subCategory != null) {
                return ResponseEntity.ok(subCategory);
            }
            return ResponseEntity.status(404).body(Constants.RESOURCE_NOT_FOUND);
        } catch (Exception e) {
            log.info("Error while getting sub category by id", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/addSubCategory")
    public ResponseEntity<?> addSubCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
        try{
            if(validation.isAdmin()){
                SubCategory subCategory = subCategoryService.addSubCategory(subCategoryRequest);
                if(subCategory != null){
                    return ResponseEntity.ok(subCategory);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while adding Sub Category", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PutMapping("/updateSubCategory")
    public ResponseEntity<?> updateSubCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
        try{
            if(validation.isAdmin()){
                SubCategory subCategory = subCategoryService.updateSubCategory(subCategoryRequest);
                if(subCategory != null){
                    return ResponseEntity.ok(subCategory);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while adding Sub Category", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("/deleteSubCategory")
    public ResponseEntity<?> deleteSubCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
        try{
            if(validation.isAdmin()){
                SubCategory subCategory = subCategoryService.deleteSubCategory(subCategoryRequest);
                if(subCategory != null){
                    return ResponseEntity.ok(subCategory);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while adding Sub Category", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
