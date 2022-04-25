package com.transonphat.carbooking.exceptions;

public class MissingPeriodException extends RuntimeException {
    public MissingPeriodException() {
    }

    public MissingPeriodException(String message) {
        super(message);
    }
}
