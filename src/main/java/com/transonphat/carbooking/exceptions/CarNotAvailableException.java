package com.transonphat.carbooking.exceptions;

public class CarNotAvailableException extends RuntimeException {
    public CarNotAvailableException() {
    }

    public CarNotAvailableException(String message) {
        super(message);
    }
}
