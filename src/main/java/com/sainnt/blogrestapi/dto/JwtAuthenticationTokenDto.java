package com.sainnt.blogrestapi.dto;

public class JwtAuthenticationTokenDto {
    private String token;
    private final String tokenType = "Bearer";

    public JwtAuthenticationTokenDto(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}
