package com.sainnt.blogrestapi.controller;

import com.sainnt.blogrestapi.dto.JwtAuthenticationTokenDto;
import com.sainnt.blogrestapi.dto.LoginDto;
import com.sainnt.blogrestapi.dto.SignupDto;
import com.sainnt.blogrestapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<JwtAuthenticationTokenDto> login(@Valid @RequestBody LoginDto loginDto){
        return ResponseEntity.ok(authenticationService.login(loginDto));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDto signupDto){
        if(authenticationService.userByUsernameExists(signupDto.getUsername()))
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        if(authenticationService.userByEmailExists(signupDto.getEmail()))
            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        authenticationService.signUp(signupDto, new String[]{"ROLE_USER"});
        return ResponseEntity.ok("Signed up successfully");
    }
}
