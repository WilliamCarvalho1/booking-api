package com.example.booking.exception;

import java.time.LocalDate;

public class CheckInDateShouldBeOneDayAfterBookingDateException extends Exception {
    public CheckInDateShouldBeOneDayAfterBookingDateException(LocalDate date) {
        super();
    }
}
