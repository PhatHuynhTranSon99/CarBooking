package com.transonphat.carbooking.exceptions;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException() {
    }

    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
