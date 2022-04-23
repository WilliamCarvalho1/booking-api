package com.example.booking.exception;

import java.time.LocalDate;

public class DateNotAvailableException extends Exception {
    public DateNotAvailableException(LocalDate date) {
        super();
    }
}
