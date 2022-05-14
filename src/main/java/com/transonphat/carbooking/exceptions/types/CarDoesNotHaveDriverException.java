package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when trying to create booking with a car that has no driver
 */
public class CarDoesNotHaveDriverException extends BadRequestException {
    public CarDoesNotHaveDriverException() {
    }

    public CarDoesNotHaveDriverException(String message) {
        super(message);
    }
}
