package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when car with specific id can not be found
 */
public class CarNotFoundException extends BadRequestException {
    public CarNotFoundException() {
    }

    public CarNotFoundException(String message) {
        super(message);
    }
}
