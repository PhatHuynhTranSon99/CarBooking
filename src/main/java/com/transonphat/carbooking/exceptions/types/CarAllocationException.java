package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

public class CarAllocationException extends BadRequestException {
    public CarAllocationException() {
    }

    public CarAllocationException(String message) {
        super(message);
    }
}
