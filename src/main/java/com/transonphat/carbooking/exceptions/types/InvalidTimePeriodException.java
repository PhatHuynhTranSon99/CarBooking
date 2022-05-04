package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

public class InvalidTimePeriodException extends BadRequestException {
    public InvalidTimePeriodException() {
    }

    public InvalidTimePeriodException(String message) {
        super(message);
    }
}
