package com.shopping.startup.controller;


import com.shopping.startup.entity.Variation;
import com.shopping.startup.model.VariationRequest;
import com.shopping.startup.service.VariationService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/variation")
@Slf4j
public class VariationController {

    private final VariationService variationService;
    private final Validation validation;

    @GetMapping("/allVariations")
    public ResponseEntity<?> getAllVariations() {
        try{
            return ResponseEntity.ok(variationService.getAllVariations());
        } catch (Exception e){
            log.info("Error while getting all variations", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/addVariation")
    public ResponseEntity<?> addVariation(@RequestBody VariationRequest variationRequest) {
        try{
            if(validation.isAdmin()){
                Variation variation = variationService.addVariation(variationRequest);
                if(variation == null){
                    return ResponseEntity.badRequest().build();
                }
                return ResponseEntity.ok(variation);
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Error while adding variation", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/updateVariation")
    public ResponseEntity<?> updateVariation(@RequestBody VariationRequest variationRequest) {
        try{
            if(validation.isAdmin()){
                Variation variation = variationService.updateVariation(variationRequest);
                if(variation == null){
                    return ResponseEntity.badRequest().build();
                }
                return ResponseEntity.ok(variation);
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Error while adding variation", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/deleteVariation")
    public ResponseEntity<?> deleteVariation(@RequestBody VariationRequest variationRequest) {
        try{
            if(validation.isAdmin()){
                Variation variation = variationService.deleteVariation(variationRequest);
                if(variation == null)
                    return ResponseEntity.badRequest().build();
                return ResponseEntity.ok(variation);
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Error while adding variation", e);
        }
        return ResponseEntity.internalServerError().build();
    }
}
