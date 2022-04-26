package com.hotel.booking.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
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
