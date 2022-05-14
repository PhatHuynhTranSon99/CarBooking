package com.transonphat.carbooking.exceptions.types;

import com.transonphat.carbooking.exceptions.BadRequestException;

/**
 * Author: Tran Son Phat
 * Raised when invoice with specific id can not be found
 */
public class InvoiceNotFoundException extends BadRequestException {
    public InvoiceNotFoundException() {
    }

    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
