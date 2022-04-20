package com.edu.tks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NotFoundExceptionView extends BasicExceptionView {
    public NotFoundExceptionView(String message) {
        super(message);
    }
}
