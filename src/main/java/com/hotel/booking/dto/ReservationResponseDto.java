package com.hotel.booking.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ReservationResponseDto {

    private Long reservationId;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private LocalDate bookingDate;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long roomId;

    private String status;

    private BigDecimal totalValue;

}
