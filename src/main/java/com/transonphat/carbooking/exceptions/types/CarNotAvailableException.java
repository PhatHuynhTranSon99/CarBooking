package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

public class CarNotAvailableException extends BadRequestException {
    public CarNotAvailableException() {
    }

    public CarNotAvailableException(String message) {
        super(message);
    }
}
