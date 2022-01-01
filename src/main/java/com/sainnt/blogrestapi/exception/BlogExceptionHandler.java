package com.sainnt.blogrestapi.exception;

import com.sainnt.blogrestapi.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class BlogExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        return new ResponseEntity<>(
                new ExceptionDto(new Date(),exception.getMessage(),request.getDescription(false)),
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ExceptionDto> handleBlogApiException(BlogApiException exception, WebRequest request){
        return new ResponseEntity<>(
                new ExceptionDto(new Date(),exception.getMessage(),request.getDescription(false)),
                HttpStatus.BAD_REQUEST
        );
    }





    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto>  handleGlobalException(Exception exception, WebRequest request){
        return new ResponseEntity<>(
                new ExceptionDto(new Date(),exception.getMessage(),request.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
