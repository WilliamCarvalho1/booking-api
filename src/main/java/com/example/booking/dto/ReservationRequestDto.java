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

    private LocalDate startDate;

    private LocalDate endDate;

    private int floor;

    private Long roomId;

    private String type;

    private BigDecimal price;

}
