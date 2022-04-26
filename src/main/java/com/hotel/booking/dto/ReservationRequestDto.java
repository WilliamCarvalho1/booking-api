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
public class ReservationRequestDto {

    private Long customerId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long roomId;

}
