package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when the car already has a driver or a driver already has a car
 */
public class CarAllocationException extends BadRequestException {
    public CarAllocationException() {
    }

    public CarAllocationException(String message) {
        super(message);
    }
}
