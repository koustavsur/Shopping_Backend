package com.shopping.startup.controller;


import com.shopping.startup.entity.Roles;
import com.shopping.startup.model.Role;
import com.shopping.startup.model.RoleRequest;
import com.shopping.startup.service.RoleService;
import com.shopping.startup.util.Constants;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final Validation validation;
    private final RoleService roleService;

    @GetMapping("/allRoles")
    public ResponseEntity<?> getAllRoles(){
        try {
            if (validation.isAdmin()) {
                return ResponseEntity.ok(roleService.findAllRoles());
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Exception occurred while getting all roles", e);
        }
        return ResponseEntity.internalServerError().body("");
    }

    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@RequestBody RoleRequest roleRequest){
        try {
            if (validation.isAdmin()) {
                Roles role = roleService.addRole(roleRequest);
                if (role == null){
                    return ResponseEntity.status(404).body("Role not found to be added");
                }
                return ResponseEntity.ok(role);
            }
            return ResponseEntity.status(403).body(Constants.ACCESS_FORBIDDEN);
        } catch (Exception e){
            log.info("Exception occurred while getting all roles", e);
        }
        return ResponseEntity.internalServerError().body("");
    }

    @DeleteMapping("/deleteRole")
    public ResponseEntity<?> deleteRole(@RequestBody RoleRequest roleRequest){
        try{
            if(validation.isAdmin()){
                Role role = Role.valueOf(roleRequest.getRole());
                roleService.deleteRole(role);
            }
        } catch (Exception e) {
            log.info("Exception occurred while deleting role", e);
            return ResponseEntity.internalServerError().body("");
        }
        return ResponseEntity.ok("Deleted Successfully");
    }

}
