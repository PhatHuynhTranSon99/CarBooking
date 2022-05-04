package com.transonphat.carbooking.exceptions;

public class BookingNotFoundException extends BadRequestException {
    public BookingNotFoundException() {
    }

    public BookingNotFoundException(String message) {
        super(message);
    }
}
