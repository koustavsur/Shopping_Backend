package com.shopping.startup.service;


import com.shopping.startup.entity.Roles;
import com.shopping.startup.model.Role;
import com.shopping.startup.model.RoleRequest;
import com.shopping.startup.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Roles> findAllRoles(){
        return roleRepository.findAll();
    }

    public Roles findRole(Role role){
        return roleRepository.findByRole(role);
    }

    public Roles addRole(Role role) {
        Roles userRole = Roles.builder()
                .role(role)
                .build();
        return roleRepository.save(userRole);
    }

    public Roles updateRole(Roles roles) {
        return roleRepository.save(roles);
    }

    public void deleteRoles(Roles roles){
        roleRepository.delete(roles);
    }

    public void deleteRole(Role role){
        Roles roles = findRole(role);
        roleRepository.delete(roles);
    }

    public Roles addRole(RoleRequest roleRequest) {
        try {
            Role role = Role.valueOf(roleRequest.getRole());
            return addRole(role);
        } catch (Exception e){
            log.info("Role not allowed.", e);
        }
        return null;
    }
}
