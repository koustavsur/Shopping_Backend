package com.shopping.startup.util;


import com.shopping.startup.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Validation {

    public boolean isAdmin() {
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal != null) {
                try{
                    Role principalRole =
                            Role.valueOf(principal.getAuthorities().stream().findFirst().get().getAuthority());
                    if(principalRole.equals(Role.ROLE_ADMIN))
                        return true;
                } catch (Exception e){
                    log.info("Exception while fetching principal Role: ", e);
                }
            }
        }
        return false;
    }

    public boolean isCurrentUser(String email) {
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal != null) {
                return principal.getUsername().equalsIgnoreCase(email.strip());
            }
        }
        return false;
    }

    public String getCurrentUserEmail(){
        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal != null)
                    return principal.getUsername();
            }
        } catch (Exception e){
            log.info("Exception while getting Current User Email", e);
        }
        return null;
    }

    public boolean isFieldValid(String val){
        return val != null && val.strip().length() != 0;
    }
}
