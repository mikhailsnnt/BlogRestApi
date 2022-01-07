package com.sainnt.blogrestapi.service;

import com.sainnt.blogrestapi.dto.LoginDto;

public interface AuthenticationService {
    void signIn(LoginDto loginDto);
}
