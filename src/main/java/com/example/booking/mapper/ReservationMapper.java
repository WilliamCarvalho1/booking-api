package com.example.booking.mapper;

import com.example.booking.dto.AlterationRequestDto;
import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.dto.ReservationResponseDto;
import com.example.booking.enums.ReservationStatus;
import com.example.booking.model.Customer;
import com.example.booking.model.Reservation;
import com.example.booking.model.Room;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.booking.utils.BookingUtils.getTotalValue;

public class ReservationMapper {

    private ReservationMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Reservation dtoToEntity(ReservationRequestDto body, Room room) {

        return Reservation.builder()
                .bookingDate(LocalDate.now())
                .checkInDate(body.getCheckInDate())
                .checkOutDate(body.getCheckOutDate())
                .roomId(room.getId())
                .customerId(body.getCustomer().getId())
                .totalValue(getTotalValue(room.getPrice(), body.getCheckInDate(), body.getCheckOutDate()))
                .status(ReservationStatus.ACTIVE)
                .build();
    }

    public static Reservation alterationRequestDtoToEntity(AlterationRequestDto body, Reservation reservation,
                                                           BigDecimal roomPrice) {

        return Reservation.builder()
                .bookingDate(LocalDate.now())
                .checkInDate(body.getCheckInDate())
                .checkOutDate(body.getCheckOutDate())
                .roomId(reservation.getRoomId())
                .customerId(reservation.getCustomerId())
                .totalValue(getTotalValue(roomPrice, body.getCheckInDate(), body.getCheckOutDate()))
                .status(ReservationStatus.ALTERED)
                .build();
    }

    public static ReservationResponseDto entityToDto(Reservation reservation, Customer customer) {

        return ReservationResponseDto.builder()
                .reservationId(reservation.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .bookingDate(reservation.getBookingDate())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .roomId(reservation.getRoomId())
                .status(reservation.getStatus())
                .totalValue(reservation.getTotalValue())
                .build();
    }

}
