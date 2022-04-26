package com.hotel.booking.helper;

import com.hotel.booking.dto.AlterationRequestDto;
import com.hotel.booking.dto.ReservationRequestDto;
import com.hotel.booking.dto.ReservationResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AlterationDtoHelper {

    public static AlterationRequestDto getAlterationRequestDto() {

        return AlterationRequestDto.builder()
                .reservationId(1L)
                .customerId(1L)
                .roomId(1L)
                .checkInDate(LocalDate.parse("2022-05-08"))
                .checkOutDate(LocalDate.parse("2022-05-10"))
                .roomId(1L)
                .build();
    }

}
