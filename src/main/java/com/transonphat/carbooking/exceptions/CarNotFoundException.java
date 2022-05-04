package com.transonphat.carbooking.exceptions;

public class CarNotFoundException extends BadRequestException {
    public CarNotFoundException() {
    }

    public CarNotFoundException(String message) {
        super(message);
    }
}
