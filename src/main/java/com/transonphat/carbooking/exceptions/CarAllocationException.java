package com.transonphat.carbooking.exceptions;

public class CarAllocationException extends BadRequestException {
    public CarAllocationException() {
    }

    public CarAllocationException(String message) {
        super(message);
    }
}
