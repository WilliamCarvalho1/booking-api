package com.example.booking.dto;

import com.example.booking.model.Customer;
import com.example.booking.model.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AlterationRequestDto {

    private Long reservationId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    public static AlterationRequestDto of(Reservation reservation, Customer customer) {

        return AlterationRequestDto.builder()
                .reservationId(reservation.getId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .build();

    }

}
