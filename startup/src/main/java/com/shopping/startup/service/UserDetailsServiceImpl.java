package com.shopping.startup.service;

import com.shopping.startup.entity.Users;
import com.shopping.startup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        UserDetails userDetails =
                new User(user.getEmail(), "password",
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRole().name())));

        return userDetails;
    }
}
