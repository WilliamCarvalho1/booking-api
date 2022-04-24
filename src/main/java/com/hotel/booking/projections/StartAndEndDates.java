package com.hotel.booking.projections;

import java.time.LocalDate;

public interface StartAndEndDates {

    LocalDate getCheckInDate();
    LocalDate getCheckOutDate();

}
