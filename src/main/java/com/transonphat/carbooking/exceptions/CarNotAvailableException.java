package com.transonphat.carbooking.exceptions;

public class CarNotAvailableException extends BadRequestException {
    public CarNotAvailableException() {
    }

    public CarNotAvailableException(String message) {
        super(message);
    }
}
