package com.shopping.startup.controller;


import com.shopping.startup.config.JwtUtil;
import com.shopping.startup.model.AuthenticationRequest;
import com.shopping.startup.model.JwtToken;
import com.shopping.startup.service.UserDetailsServiceImpl;
import com.shopping.startup.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        log.info("Authentication Endpoint called");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), "password"));
        } catch (BadCredentialsException ex){
            log.info("Create user");
            userService.createUser(authenticationRequest);
        }
        catch (Exception e){
            log.info("Check the exception", e);
        }

        UserDetails user = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        if(user != null){
            JwtToken jwtToken = JwtToken.builder().jwtToken(jwtUtil.generateToken(user)).build();
            log.info("JWT Token created");
            return ResponseEntity.ok(jwtToken);
        }

        return ResponseEntity.status(400).body("User cannot be processed");

    }
}
