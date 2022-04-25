package com.hotel.booking.exception;

import java.io.Serial;

public class CustomerNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
