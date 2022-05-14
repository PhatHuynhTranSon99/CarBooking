package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when booking with specific id can not be found
 */
public class BookingNotFoundException extends BadRequestException {
    public BookingNotFoundException() {
    }

    public BookingNotFoundException(String message) {
        super(message);
    }
}
