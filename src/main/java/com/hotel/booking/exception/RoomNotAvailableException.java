package com.hotel.booking.exception;

import java.io.Serial;

public class RoomNotAvailableException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RoomNotAvailableException(String msg) {
        super(msg);
    }
}
