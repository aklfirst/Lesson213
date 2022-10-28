package com.example.lesson213.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class EmployeeIncorrectEntryException extends RuntimeException {

    public EmployeeIncorrectEntryException(String message) {
        super(message);
    }

}
