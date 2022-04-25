package com.hotel.booking.exception;

import java.io.Serial;

public class ReservationDoesNotExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ReservationDoesNotExistException(String msg) {
        super(msg);
    }
}
