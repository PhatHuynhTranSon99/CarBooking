package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

public class MissingPeriodException extends BadRequestException {
    public MissingPeriodException() {
    }

    public MissingPeriodException(String message) {
        super(message);
    }
}
