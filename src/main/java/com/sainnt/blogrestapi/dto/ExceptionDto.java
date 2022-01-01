package com.sainnt.blogrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ExceptionDto {
    private Date date;
    private String message;
    private String description;
}
