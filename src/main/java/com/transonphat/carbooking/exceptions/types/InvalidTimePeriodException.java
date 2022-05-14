package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when only the from date or the end date (not both) was provided as query parameters but.
 */
public class InvalidTimePeriodException extends BadRequestException {
    public InvalidTimePeriodException(String message) {
        super(message);
    }
}
