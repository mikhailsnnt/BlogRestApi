package com.sainnt.blogrestapi.controller;

import com.sainnt.blogrestapi.dto.LoginDto;
import com.sainnt.blogrestapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto){
        authenticationService.signIn(loginDto);
        return ResponseEntity.ok("Logged in successfully");
    }
}
