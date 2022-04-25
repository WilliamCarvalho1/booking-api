package com.hotel.booking.exception;

import java.io.Serial;

public class CustomerDoesNotMatchException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CustomerDoesNotMatchException(String msg) {
        super(msg);
    }
}
