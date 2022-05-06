package com.edu.tks.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BasicExceptionView extends Exception {
    public BasicExceptionView(String message) {
        super(message);
    }
}
