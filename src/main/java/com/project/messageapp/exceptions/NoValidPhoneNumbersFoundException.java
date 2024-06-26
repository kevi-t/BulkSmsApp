package com.project.messageapp.exceptions;

public class NoValidPhoneNumbersFoundException extends RuntimeException {
    public NoValidPhoneNumbersFoundException(String message) {
        super(message);
    }
}