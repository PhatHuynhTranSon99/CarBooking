package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when trying to create booking but the car is not available for the trip (has overlapping trips)
 */
public class CarNotAvailableException extends BadRequestException {
    public CarNotAvailableException() {
    }

    public CarNotAvailableException(String message) {
        super(message);
    }
}
