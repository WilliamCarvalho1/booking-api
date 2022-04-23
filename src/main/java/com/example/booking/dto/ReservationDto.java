package com.example.booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReservationDto {

    private Long id;

    private String customerId;

    private String roomId;

    private LocalDate bookingDate;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private BigDecimal totalValue;

}
