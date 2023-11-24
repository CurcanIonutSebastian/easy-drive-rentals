package com.ionut.easydriverentals.exceptions;

public class DataExistsException extends RuntimeException {
    public DataExistsException(String message) {
        super(message);
    }
}