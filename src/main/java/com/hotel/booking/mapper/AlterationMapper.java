package com.hotel.booking.mapper;

import com.hotel.booking.dto.AlterationRequestDto;
import com.hotel.booking.enums.ReservationStatus;
import com.hotel.booking.model.Reservation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.hotel.booking.utils.BookingUtils.getTotalValue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlterationMapper {

    public static Reservation alterationRequestDtoToEntity(AlterationRequestDto body, Reservation reservation,
                                                           BigDecimal roomPrice) {
        return Reservation.builder()
                .id(body.getReservationId())
                .bookingDate(LocalDate.now())
                .checkInDate(body.getCheckInDate())
                .checkOutDate(body.getCheckOutDate())
                .roomId(reservation.getRoomId())
                .customerId(reservation.getCustomerId())
                .totalValue(getTotalValue(roomPrice, body.getCheckInDate(), body.getCheckOutDate()))
                .status(ReservationStatus.ALTERED.getStatus())
                .build();
    }

}
