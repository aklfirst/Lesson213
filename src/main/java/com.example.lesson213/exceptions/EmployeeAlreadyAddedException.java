package com.example.lesson213.exceptions;


public class EmployeeAlreadyAddedException extends RuntimeException {

    public EmployeeAlreadyAddedException(String message) {
        super(message);
    }

}
