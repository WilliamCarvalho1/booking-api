package com.hotel.booking.mapper;

import com.hotel.booking.dto.ReservationRequestDto;
import com.hotel.booking.dto.ReservationResponseDto;
import com.hotel.booking.enums.ReservationStatus;
import com.hotel.booking.model.Customer;
import com.hotel.booking.model.Reservation;
import com.hotel.booking.model.Room;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.hotel.booking.utils.BookingUtils.getTotalValue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationMapper {

    public static Reservation dtoToEntity(ReservationRequestDto body, Room room) {
        return Reservation.builder()
                .bookingDate(LocalDate.now())
                .checkInDate(body.getCheckInDate())
                .checkOutDate(body.getCheckOutDate())
                .roomId(room.getId())
                .customerId(body.getCustomerId())
                .totalValue(getTotalValue(room.getPrice(), body.getCheckInDate(), body.getCheckOutDate()))
                .status(ReservationStatus.ACTIVE.getStatus())
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
