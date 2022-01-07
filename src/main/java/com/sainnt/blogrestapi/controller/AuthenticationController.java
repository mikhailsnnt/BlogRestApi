package com.sainnt.blogrestapi.controller;

import com.sainnt.blogrestapi.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/auth")
public class AuthenticationController {
    private final AuthenticationManager manager;
    @Autowired
    public AuthenticationController(AuthenticationManager manager) {
        this.manager = manager;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Logged in successfully");
    }
}
