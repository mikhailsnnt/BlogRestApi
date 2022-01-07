package com.sainnt.blogrestapi.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginDto {
    @NotEmpty
    @Size(min = 2, max = 320, message = "Username or email size should be  greater then 2 and less than 320 symbols")
    private String usernameOrEmail;
    @NotEmpty
    @Size(min = 5, max= 100, message = "Password size should be greater then 5 and less than 100 symbols")
    private String password;
}
