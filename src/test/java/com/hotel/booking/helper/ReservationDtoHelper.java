package com.hotel.booking.helper;

import com.hotel.booking.dto.ReservationRequestDto;
import com.hotel.booking.dto.ReservationResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationDtoHelper {

    public static ReservationRequestDto getReservationRequestDto() {

        return ReservationRequestDto.builder()
                .customerId(1L)
                .checkInDate(LocalDate.parse("2022-05-08"))
                .checkOutDate(LocalDate.parse("2022-05-10"))
                .roomId(1L)
                .build();
    }

    public static ReservationResponseDto getReservationResponseDto() {

        return ReservationResponseDto.builder()
                .reservationId(1L)
                .firstName("Michael")
                .lastName("Jordan")
                .phone("1234-1234")
                .email("michael.jordan@email.com")
                .bookingDate(LocalDate.now())
                .checkInDate(LocalDate.parse("2022-05-08"))
                .checkOutDate(LocalDate.parse("2022-05-10"))
                .roomId(1L)
                .status("active")
                .totalValue(BigDecimal.valueOf(200))
                .build();
    }

    public static ReservationResponseDto getCanceledReservationResponseDto() {
        var dto = getReservationResponseDto();
        dto.setStatus("canceled");

        return dto;
    }

    public static ReservationResponseDto getModifiedReservationResponseDto() {
        var dto = getReservationResponseDto();
        dto.setStatus("altered");

        return dto;
    }

}
