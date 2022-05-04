package com.transonphat.carbooking.exceptions;

public class CarDoesNotHaveDriverException extends BadRequestException {
    public CarDoesNotHaveDriverException() {
    }

    public CarDoesNotHaveDriverException(String message) {
        super(message);
    }
}
