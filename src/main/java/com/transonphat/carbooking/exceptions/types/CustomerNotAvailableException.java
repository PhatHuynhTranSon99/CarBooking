package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

public class CustomerNotAvailableException extends BadRequestException {
    public CustomerNotAvailableException() {
    }

    public CustomerNotAvailableException(String message) {
        super(message);
    }
}
