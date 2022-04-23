package com.example.booking.service;

import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.dto.ReservationResponseDto;
import com.example.booking.dto.RoomsAvailabilityDto;
import com.example.booking.enums.ReservationStatus;
import com.example.booking.model.Reservation;
import com.example.booking.repository.CustomerRepository;
import com.example.booking.repository.ReservationRepository;
import com.example.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookingService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<RoomsAvailabilityDto> getAvailability(LocalDate checkInDate, LocalDate checkOutDate) {

        List<RoomsAvailabilityDto> roomsAvailability = new ArrayList<>(0);
        List<LocalDate> unavailableDates = new ArrayList<>(0);

        var freeRooms = roomRepository.findAllAvailableRooms();


        freeRooms.forEach(r -> {
            unavailableDates.addAll(reservationRepository.findUnavailableDates(r.getId()));

        });


        return null;
    }

    public ReservationResponseDto createReservation(ReservationRequestDto body) {

        var room = roomRepository.findById(body.getRoomId());

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

    public boolean isAvailableDate(LocalDate checkInDate, LocalDate checkOutDate){



        return false;

    }

}
