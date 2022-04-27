package com.hotel.booking.projections;

import java.time.LocalDate;

public interface RoomsBookedDates {

    Long getRoomId();

    LocalDate getCheckInDate();

    LocalDate getCheckOutDate();

}
