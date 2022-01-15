package com.sainnt.blogrestapi.service;

import com.sainnt.blogrestapi.dto.JwtAuthenticationTokenDto;
import com.sainnt.blogrestapi.dto.LoginDto;
import com.sainnt.blogrestapi.dto.SignupDto;

public interface AuthenticationService {
    boolean userByUsernameExists(String username);
    boolean userByEmailExists(String email);
    JwtAuthenticationTokenDto login(LoginDto loginDto);
    void signUp(SignupDto signupDto, String[] roles);
}
