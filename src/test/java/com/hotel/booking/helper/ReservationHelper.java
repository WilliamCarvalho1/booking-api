package com.hotel.booking.helper;

import com.hotel.booking.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationHelper {

    public static Reservation getReservation() {

        return Reservation.builder()
                .id(1L)
                .customerId(1L)
                .roomId(1L)
                .bookingDate(LocalDate.parse("2022-05-08"))
                .checkInDate(LocalDate.parse("2022-05-08"))
                .checkOutDate(LocalDate.parse("2022-05-10"))
                .status("active")
                .totalValue(BigDecimal.valueOf(200))
                .build();
    }

}
