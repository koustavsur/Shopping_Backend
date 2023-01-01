package com.shopping.startup.controller;


import com.shopping.startup.entity.UserAddress;
import com.shopping.startup.model.AddressRequest;
import com.shopping.startup.service.AddressService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
@Slf4j
public class AddressController {

    private final AddressService addressService;
    private final Validation validation;

    @GetMapping("/allAddress")
    public ResponseEntity<?> getAllAddress() {
        try{
            if(validation.isAdmin()){
                return ResponseEntity.ok(addressService.getAllAddress());
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {

        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddress(@RequestBody AddressRequest addressRequest) {
        try{
            UserAddress address = addressService.addAddress(addressRequest);
            if(address != null) {
                return ResponseEntity.ok(address);
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while adding address", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<?> updateAddress(@RequestBody AddressRequest addressRequest) {
        try{
            UserAddress address = addressService.updateAddress(addressRequest);
            if(address != null) {
                return ResponseEntity.ok(address);
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while updating address", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/deleteAddress")
    public ResponseEntity<?> deleteAddress(@RequestBody AddressRequest addressRequest) {
        try{
            UserAddress address = addressService.deleteAddress(addressRequest);
            if(address != null) {
                return ResponseEntity.ok(address);
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while deleting address", e);
        }
        return ResponseEntity.internalServerError().build();
    }

}
