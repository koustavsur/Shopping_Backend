package com.shopping.startup.controller;


import com.shopping.startup.entity.Category;
import com.shopping.startup.model.CategoryRequest;
import com.shopping.startup.service.CategoryService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final Validation validation;

    @GetMapping("/allCategories")
    public ResponseEntity<?> getAllCategories(){
        try{
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (Exception e){
            log.info("Error while getting all categories", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable(value = "id") String id){
        try{
            Category category = categoryService.getCategory(id);
            if(category != null) {
                return ResponseEntity.ok(category);
            }
            return ResponseEntity.status(404).body(Constants.RESOURCE_NOT_FOUND);
        } catch (Exception e) {
            log.info("Error while getting category by id", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest categoryRequest) {
        try{
            if(validation.isAdmin()){
                Category category = categoryService.addCategory(categoryRequest);
                if(category != null){
                    return ResponseEntity.ok(category);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while adding category", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest categoryRequest) {
        try{
            if(validation.isAdmin()){
                Category category = categoryService.updateCategory(categoryRequest);
                if(category != null){
                    return ResponseEntity.ok(category);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while updating category", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<?> deleteCategory(@RequestBody CategoryRequest categoryRequest) {
        try{
            if(validation.isAdmin()){
                Category category = categoryService.deleteCategory(categoryRequest);
                if(category != null){
                    return ResponseEntity.ok(category);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while updating category", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

}
