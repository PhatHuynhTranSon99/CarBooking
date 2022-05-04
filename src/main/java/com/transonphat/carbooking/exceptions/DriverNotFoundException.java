package com.transonphat.carbooking.exceptions;

public class DriverNotFoundException extends BadRequestException {
    public DriverNotFoundException() {
    }

    public DriverNotFoundException(String message) {
        super(message);
    }
}
