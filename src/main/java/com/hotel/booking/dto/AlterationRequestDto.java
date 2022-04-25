package com.hotel.booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AlterationRequestDto {

    private Long reservationId;

    private Long customerId;

    private Long roomId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

}
