package com.example.authenticationunittesting.exception;

public class NotFoundException extends ApiRequestException{
    public NotFoundException(String subject) {
        super("Sorry "+subject+" is not found");
    }
}
