package com.transonphat.carbooking.exceptions;

public class CarDoesNotHaveDriverException extends RuntimeException {
    public CarDoesNotHaveDriverException() {
    }

    public CarDoesNotHaveDriverException(String message) {
        super(message);
    }
}
