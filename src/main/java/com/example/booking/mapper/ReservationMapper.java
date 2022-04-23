package com.example.booking.mapper;

import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.enums.ReservationStatus;
import com.example.booking.model.Reservation;
import com.example.booking.model.Room;

import java.time.LocalDate;

import static com.example.booking.utils.BookingUtils.getTotalValue;

public class ReservationMapper {

    private ReservationMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Reservation dtoToEntity(ReservationRequestDto body, Room room) {
        return Reservation.builder()
                .bookingDate(LocalDate.now())
                .checkInDate(body.getCheckInDate())
                .checkOutDate(body.getCheckOutDate())
                .roomId(room.getId())
                .customerId(body.getCustomer().getId())
                .totalValue(getTotalValue(room.getPrice(), body.getCheckInDate(), body.getCheckOutDate()))
                .status(ReservationStatus.ACTIVE)
                .build();
    }


}
