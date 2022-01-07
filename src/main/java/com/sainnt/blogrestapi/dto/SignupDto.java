package com.sainnt.blogrestapi.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    @NotEmpty
    @Size(min = 2, max = 320, message = "Username size should be  greater then 2 and less than 320 symbols")
    private String username;
    @NotEmpty
    @Size(min = 5, max= 100, message = "Password size should be greater then 5 and less than 100 symbols")
    private String password;
    @NotEmpty
    @Size(min = 2, max = 100, message = "Name size should be  greater then 2 and less than 100 symbols")
    private String name;
    @NotEmpty
    @Email
    private String email;
}
