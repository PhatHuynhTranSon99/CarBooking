package com.transonphat.carbooking.exceptions;

public class MissingPeriodException extends BadRequestException {
    public MissingPeriodException() {
    }

    public MissingPeriodException(String message) {
        super(message);
    }
}
