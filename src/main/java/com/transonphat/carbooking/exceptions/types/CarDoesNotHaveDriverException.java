package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

public class CarDoesNotHaveDriverException extends BadRequestException {
    public CarDoesNotHaveDriverException() {
    }

    public CarDoesNotHaveDriverException(String message) {
        super(message);
    }
}
