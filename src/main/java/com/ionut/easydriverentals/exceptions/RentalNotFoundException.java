package com.ionut.easydriverentals.exceptions;


public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException(String message) {
        super(message);
    }
}
