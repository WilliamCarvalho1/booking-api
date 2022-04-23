package com.example.booking.projections;

import java.time.LocalDate;

public interface StartAndEndDates {

    LocalDate getCheckInDate();
    LocalDate getCheckOutDate();

}
