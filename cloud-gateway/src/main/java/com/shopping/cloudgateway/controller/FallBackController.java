package com.shopping.cloudgateway.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FallBackController {

    @GetMapping("/shoppingServiceFallback")
    public ResponseEntity<?> shoppingServiceFallbackMethod() {
        return ResponseEntity.status(503).body("Maintenance mode is ON");
    }

    @PostMapping("/shoppingServiceFallback")
    public ResponseEntity<?> shoppingServiceFallbackMethodPost() {
        return ResponseEntity.status(503).body("Maintenance mode is ON");
    }

    @PutMapping("/shoppingServiceFallback")
    public ResponseEntity<?> shoppingServiceFallbackMethodPut() {
        return ResponseEntity.status(503).body("Maintenance mode is ON");
    }

    @DeleteMapping("/shoppingServiceFallback")
    public ResponseEntity<?> shoppingServiceFallbackMethodDelete() {
        return ResponseEntity.status(503).body("Maintenance mode is ON");
    }
}
