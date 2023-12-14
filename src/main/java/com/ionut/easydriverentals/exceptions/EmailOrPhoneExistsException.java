package com.ionut.easydriverentals.exceptions;

public class EmailOrPhoneExistsException extends RuntimeException {
    public EmailOrPhoneExistsException(String message) {
        super(message);
    }
}