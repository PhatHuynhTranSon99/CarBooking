package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when trying to create booking but the customer is not available for the trip (has overlapping trips)
 */
public class CustomerNotAvailableException extends BadRequestException {
    public CustomerNotAvailableException() {
    }

    public CustomerNotAvailableException(String message) {
        super(message);
    }
}
