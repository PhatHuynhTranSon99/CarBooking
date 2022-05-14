package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when driver with specific id can not be found
 */
public class DriverNotFoundException extends BadRequestException {
    public DriverNotFoundException() {
    }

    public DriverNotFoundException(String message) {
        super(message);
    }
}
