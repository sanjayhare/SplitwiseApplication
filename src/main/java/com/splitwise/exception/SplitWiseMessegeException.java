package com.splitwise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SplitWiseMessegeException extends RuntimeException {
    public SplitWiseMessegeException(String message) {
        super(message);
    }
}

