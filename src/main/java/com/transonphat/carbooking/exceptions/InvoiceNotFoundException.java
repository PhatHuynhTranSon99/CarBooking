package com.transonphat.carbooking.exceptions;

public class InvoiceNotFoundException extends BadRequestException {
    public InvoiceNotFoundException() {
    }

    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
