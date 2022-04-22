package com.example.booking.service;

import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.dto.ReservationResponseDto;
import com.example.booking.dto.RoomsAvailabilityDto;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {

    ReservationResponseDto createReservation(ReservationRequestDto body);

    ReservationResponseDto modifyReservation(ReservationRequestDto body);

    ReservationResponseDto deleteReservation(ReservationRequestDto body);

    List<RoomsAvailabilityDto> getAvailability(LocalDate startDate, LocalDate endDate);
}
