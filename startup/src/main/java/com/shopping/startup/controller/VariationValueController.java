package com.shopping.startup.controller;

import com.shopping.startup.entity.VariationValue;
import com.shopping.startup.model.VariationValueRequest;
import com.shopping.startup.service.VariationValueService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/variationValue")
@Slf4j
public class VariationValueController {

    private final VariationValueService variationValueService;
    private final Validation validation;

    @GetMapping("/allVariationValues")
    public ResponseEntity<?> getAllVariationValues() {
        try{
            return ResponseEntity.ok(variationValueService.getAllVariationValues());
        } catch (Exception e){
            log.info("Error while getting all variation values", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/addVariationValue")
    public ResponseEntity<?> addVariationValue(@RequestBody VariationValueRequest variationValueRequest) {
        try{
            if(validation.isAdmin()){
                VariationValue variationValue = variationValueService.addVariationValue(variationValueRequest);
                if(variationValue != null){
                    return ResponseEntity.ok(variationValue);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Error while adding variation value", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/updateVariationValue")
    public ResponseEntity<?> updateVariationValue(@RequestBody VariationValueRequest variationValueRequest) {
        try{
            if(validation.isAdmin()){
                VariationValue variationValue = variationValueService.updateVariationValue(variationValueRequest);
                if(variationValue != null){
                    return ResponseEntity.ok(variationValue);
                }
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Error while updating variation value", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/deleteVariationValue")
    public ResponseEntity<?> deleteVariationValue(@RequestBody VariationValueRequest variationValueRequest) {
        try{
            if(validation.isAdmin()){
                VariationValue variationValue = variationValueService.deleteVariation(variationValueRequest);
                if(variationValue == null)
                    return ResponseEntity.badRequest().build();

                return ResponseEntity.ok(variationValue);
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Error while deleting variation value", e);
        }
        return ResponseEntity.internalServerError().build();
    }


}
