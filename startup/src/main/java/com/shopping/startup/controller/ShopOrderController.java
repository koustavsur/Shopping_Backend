package com.shopping.startup.controller;


import com.shopping.startup.entity.ShopOrder;
import com.shopping.startup.model.ShopOrderRequest;
import com.shopping.startup.service.ShopOrderService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopOrder")
@Slf4j
public class ShopOrderController {

    private final ShopOrderService shopOrderService;
    private final Validation validation;

    @GetMapping("/allOrders")
    public ResponseEntity<?> getAllOrders(){
        try{
            if(validation.isAdmin()){
                return (ResponseEntity.ok(shopOrderService.getAllOrders()));
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Exception while getting all orders", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/byStatus/{orderStatus}")
    public ResponseEntity<?> getAllOrdersByStatus(@PathVariable String orderStatus){
        try{
            if(validation.isAdmin()){
                List<ShopOrder> orders = shopOrderService.getAllOrderByStatus(orderStatus);
                if(orders == null)
                    return ResponseEntity.badRequest().build();

                return (ResponseEntity.ok(orders));
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Exception while getting orders by status", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/orders/{userEmail}")
    public ResponseEntity<?> getAllOrdersByUser(@PathVariable String userEmail){
        try{
            if(validation.isAdmin()){
                List<ShopOrder> orders = shopOrderService.getAllOrderByUser(userEmail);
                if(orders == null)
                    return ResponseEntity.badRequest().build();

                return (ResponseEntity.ok(orders));
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Exception while getting orders by user", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/currentUserOrders")
    public ResponseEntity<?> getAllCurrentUsersOrders(){
        try{
            List<ShopOrder> orders = shopOrderService.getAllCurrentUserOrders();
            if(orders == null)
                return ResponseEntity.badRequest().build();

            return (ResponseEntity.ok(orders));
        } catch (Exception e){
            log.info("Exception while getting orders by user", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@RequestBody ShopOrderRequest orderRequest){
        try{
            ShopOrder order = shopOrderService.addOrder(orderRequest);
            if(order == null)
                return ResponseEntity.badRequest().build();

            return (ResponseEntity.ok(order));
        } catch (Exception e){
            log.info("Exception while getting orders by user", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<?> updateOrder(@RequestBody ShopOrderRequest orderRequest){
        try{
            ShopOrder order = shopOrderService.updateOrder(orderRequest);
            if(order == null)
                return ResponseEntity.badRequest().build();

            return (ResponseEntity.ok(order));
        } catch (Exception e){
            log.info("Exception while getting orders by user", e);
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/deleteOrders")
    public ResponseEntity<?> deleteOrders(@RequestBody List<ShopOrderRequest> orderRequest){
        try{
            if(validation.isAdmin()) {
                ShopOrder order = shopOrderService.deleteOrders(orderRequest);
                if (order == null)
                    return ResponseEntity.badRequest().build();

                return (ResponseEntity.ok(order));
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Exception while getting orders by user", e);
        }
        return ResponseEntity.internalServerError().build();
    }

}
