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
public class ReservationResponseDto {

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;


    public static ReservationResponseDto of(Reservation reservation, Customer customer) {

        return ReservationResponseDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .build();

    }

}
