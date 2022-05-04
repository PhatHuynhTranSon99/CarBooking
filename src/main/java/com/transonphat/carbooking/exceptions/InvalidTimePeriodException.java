package com.transonphat.carbooking.exceptions;

public class InvalidTimePeriodException extends BadRequestException {
    public InvalidTimePeriodException() {
    }

    public InvalidTimePeriodException(String message) {
        super(message);
    }
}
