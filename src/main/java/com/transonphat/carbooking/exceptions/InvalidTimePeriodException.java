package com.transonphat.carbooking.exceptions;

public class InvalidTimePeriodException extends RuntimeException {
    public InvalidTimePeriodException() {
    }

    public InvalidTimePeriodException(String message) {
        super(message);
    }
}
