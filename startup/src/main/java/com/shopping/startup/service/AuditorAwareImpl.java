package com.shopping.startup.service;

import com.shopping.startup.util.Constants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        User principal = null;
        String modifier = null;
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().contains(Constants.ANONYMOUS_USER)){
                modifier = Constants.ANONYMOUS_USER;
            } else {
                principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        }

        modifier = modifier != null ? modifier :
                (principal != null ? principal.getUsername() : "UnAuthenticated User");

        return Optional.of(modifier);
    }
}
