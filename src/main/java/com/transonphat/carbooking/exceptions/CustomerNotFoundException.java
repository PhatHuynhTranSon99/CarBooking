package com.transonphat.carbooking.exceptions;

public class CustomerNotFoundException extends BadRequestException {
    public CustomerNotFoundException() {
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
