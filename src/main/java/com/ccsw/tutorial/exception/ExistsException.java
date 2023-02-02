package com.ccsw.tutorial.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_GATEWAY)
public class ExistsException extends RuntimeException implements Serializable {
    public ExistsException(String message) {
        super(message);
    }
}
