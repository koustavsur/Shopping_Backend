package com.shopping.startup.service;


import com.shopping.startup.entity.Roles;
import com.shopping.startup.entity.Users;
import com.shopping.startup.model.AuthenticationRequest;
import com.shopping.startup.model.LoginSource;
import com.shopping.startup.model.Role;
import com.shopping.startup.repository.RoleRepository;
import com.shopping.startup.repository.UserRepository;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Validation validation;

    public Users createUser(AuthenticationRequest request){

        try {
            Role userRole = Role.ROLE_USER;
            try {
                userRole = Role.valueOf(request.getRole().strip());
            } catch (Exception e) {
                log.info("Exception while getting role from request, defaulting to USER", e);
            }

            Roles role = roleRepository.findByRole(userRole);

            if(role == null){
                throw new RuntimeException("Role not available");
            }

            LoginSource loginSource = LoginSource.GOOGLE;
            try {
                loginSource = LoginSource.valueOf(request.getLoginSource());
            } catch (Exception e) {
                log.info("Exception while getting login source, defaulting to google", e);
            }


            Users user = Users.builder()
                    .email(request.getEmail().strip())
                    .imageUrl(request.getImageUrl().strip())
                    .name(request.getName().strip())
                    .loginSource(loginSource)
                    .role(role)
                    .build();

            return userRepository.save(user);
        } catch (Exception e){
            log.info("Exception while saving user", e);
        }
        return null;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getCurrentUser() {
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal != null) {
                return userRepository.findByEmail(principal.getUsername());
            }
        }
        return null;
    }

    public Users updateUser(AuthenticationRequest request) {
        if(request.getEmail() == null) {
           throw new RuntimeException("Email not present in request");
        }
        if(validation.isCurrentUser(request.getEmail()) || validation.isAdmin()) {
            Users user = userRepository.findByEmail(request.getEmail().strip());
            user.setImageUrl(request.getImageUrl().strip());
            user.setName(request.getName().strip());
            user.setLoginSource(LoginSource.valueOf(request.getLoginSource().strip()));
            user.setRole(roleRepository.findByRole(Role.valueOf(request.getRole().strip())));

            return userRepository.save(user);
        }
        return null;
    }

    @Transactional
    public void deleteUser(AuthenticationRequest request) {
        if(request.getEmail() == null) {
            throw new RuntimeException("Email not present in request");
        }

        userRepository.deleteUsersByEmail(request.getEmail());

    }
}
