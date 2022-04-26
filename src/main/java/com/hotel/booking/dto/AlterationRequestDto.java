package com.hotel.booking.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AlterationRequestDto {

    private Long reservationId;

    private Long customerId;

    private Long roomId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

}
