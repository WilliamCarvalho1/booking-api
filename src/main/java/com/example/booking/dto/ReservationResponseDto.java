package com.example.booking.dto;

import com.example.booking.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
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

    private ReservationStatus status;

    private BigDecimal totalValue;

}
