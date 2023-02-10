package com.ccsw.tutorial.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.BAD_GATEWAY)

public class InvalidDateException  extends RuntimeException implements Serializable {
    
    public InvalidDateException(String message) {
        super(message);
    }
}

