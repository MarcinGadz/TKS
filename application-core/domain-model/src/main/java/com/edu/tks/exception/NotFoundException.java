package com.edu.tks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NotFoundException extends BasicException {
    public NotFoundException(String message) {
        super(message);
    }
}