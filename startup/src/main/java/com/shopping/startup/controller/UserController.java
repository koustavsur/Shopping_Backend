package com.shopping.startup.controller;


import com.shopping.startup.entity.Users;
import com.shopping.startup.model.AuthenticationRequest;
import com.shopping.startup.service.UserService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final Validation validation;
    private final UserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers(){
        log.info("Get All Users called");
        try {
            if (validation.isAdmin()) {
                return ResponseEntity.ok(userService.getAllUsers());
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while getting all users", e);
        }
        return ResponseEntity.internalServerError().body("");
    }

    @GetMapping("/currentUserDetails")
    public ResponseEntity<?> getCurrentUserDetail(){
        try{
            Users user = userService.getCurrentUser();
            if(user == null){
                return ResponseEntity.status(404).body("User not found");
            }
            return ResponseEntity.ok(user);
        } catch (Exception e){
            log.info("Exception while getting current user detail", e);
        }
        return ResponseEntity.internalServerError().body("");
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody AuthenticationRequest request){
        try{
            Users user = userService.updateUser(request);
            if(user == null){
                return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e){
            log.info("Exception while updating user detail", e);
        }
        return ResponseEntity.internalServerError().body("Email not present in request");
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody AuthenticationRequest request){
        try{
            if(validation.isAdmin()){
                userService.deleteUser(request);
                return ResponseEntity.ok("");
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e) {
            log.info("Exception while deleting user", e);
        }
        return ResponseEntity.internalServerError().body("Email not present in request");
    }
}
