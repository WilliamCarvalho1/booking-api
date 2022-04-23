package com.example.booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReservationRequestDto {

    private CustomerDto customer;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long roomId;

    private BigDecimal price;

}
