package com.example.booking.service;

import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.dto.ReservationResponseDto;
import com.example.booking.enums.ReservationStatus;
import com.example.booking.model.Reservation;
import com.example.booking.repository.CustomerRepository;
import com.example.booking.repository.ReservationRepository;
import com.example.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.booking.utils.BookingUtils.getDatesBetween;

@Service
@Transactional
public class BookingService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<LocalDate> getAvailability(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {

        List<LocalDate> roomsAvailability = new ArrayList<>(0);

        var periodToBeBooked = getDatesBetween(checkInDate, checkOutDate);

        var reservedPeriods = reservationRepository.findUnavailableDates(roomId);

        reservedPeriods.forEach(reservedPeriod -> {
            var unavailableDates = getDatesBetween(reservedPeriod.getCheckInDate(), reservedPeriod.getCheckOutDate());
            periodToBeBooked.forEach(date -> {
                if (!unavailableDates.contains(date)) {
                    roomsAvailability.add(date);
                }
            });
        });

        return roomsAvailability;
    }

    public ReservationResponseDto createReservation(ReservationRequestDto body) {

        var room = roomRepository.findById(body.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room is not available"));

        var customer = customerRepository.findById(body.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer doesn't exist"));

        var numberOfDays = Duration.between(body.getCheckOutDate().atStartOfDay(), body.getCheckInDate().atStartOfDay()).toDays();

        var totalValue = room.getPrice().multiply(BigDecimal.valueOf(numberOfDays));

        var reservation = Reservation.builder()
                .bookingDate(LocalDate.now())
                .checkInDate(body.getCheckInDate())
                .checkOutDate(body.getCheckOutDate())
                .roomId(room.getId())
                .customerId(body.getCustomer().getId())
                .totalValue(totalValue)
                .status(ReservationStatus.ACTIVE)
                .build();

        reservationRepository.saveAndFlush(reservation);

        return ReservationResponseDto.of(reservation, customer);
    }

    public ReservationResponseDto modifyReservation(ReservationRequestDto body) {
        return null;
    }

    public ReservationResponseDto deleteReservation(ReservationRequestDto body) {
        return null;
    }

    public boolean isAvailableDate(LocalDate checkInDate, LocalDate checkOutDate) {


        return false;

    }

}
