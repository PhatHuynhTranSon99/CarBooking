package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when customer with specific id can not be found
 */
public class CustomerNotFoundException extends BadRequestException {
    public CustomerNotFoundException() {
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
