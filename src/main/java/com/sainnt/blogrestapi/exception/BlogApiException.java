package com.sainnt.blogrestapi.exception;

public class BlogApiException extends RuntimeException{

    public BlogApiException(String message) {
        super(message);
    }

    public BlogApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
