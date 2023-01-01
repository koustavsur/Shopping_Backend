package com.shopping.startup.controller;


import com.shopping.startup.entity.ShoppingCart;
import com.shopping.startup.model.ShoppingCartRequest;
import com.shopping.startup.service.ShoppingCartService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shoppingCart")
@Slf4j
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final Validation validation;

    @GetMapping("/currentUserCart")
    public ResponseEntity<?> getCurrentUserCart(){
        try{
            List<ShoppingCart> shoppingCarts = shoppingCartService.getCurrentUserCart();
            if(shoppingCarts == null)
                return ResponseEntity.badRequest().build();

            return ResponseEntity.ok(shoppingCarts);
        } catch (Exception e) {
            log.info("Exception while getting current user cart", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/userCart/{userEmail}")
    public ResponseEntity<?> getUserCart(@PathVariable(value = "userEmail") String userEmail){
        try{
            if(validation.isAdmin()) {
                List<ShoppingCart> shoppingCarts = shoppingCartService.getUserCart(userEmail);
                if (shoppingCarts == null)
                    return ResponseEntity.badRequest().build();

                return ResponseEntity.ok(shoppingCarts);
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while getting user cart", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/addUserCart")
    public ResponseEntity<?> addUserCart(@RequestBody List<ShoppingCartRequest> shoppingCartRequests){
        try{
            List<ShoppingCart> shoppingCarts = shoppingCartService.addOrUpdateUserCart(shoppingCartRequests);
            if(shoppingCarts == null)
                return ResponseEntity.badRequest().build();

            return ResponseEntity.ok(shoppingCarts);
        } catch (Exception e) {
            log.info("Exception while adding/updating user cart", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/deleteUserCart")
    public ResponseEntity<?> deleteUserCart(@RequestBody List<ShoppingCartRequest> shoppingCartRequests){
        try{
            ShoppingCart shoppingCart = shoppingCartService.deleteUserCart(shoppingCartRequests);
            if(shoppingCart == null)
                return ResponseEntity.badRequest().build();

            return ResponseEntity.ok(shoppingCart);
        } catch (Exception e) {
            log.info("Exception while deleting user cart", e);
        }
        return ResponseEntity.internalServerError().build();
    }
}
