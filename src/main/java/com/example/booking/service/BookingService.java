package com.example.booking.service;

import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.dto.ReservationResponseDto;
import com.example.booking.dto.RoomsAvailabilityDto;
import com.example.booking.repository.CustomerRepository;
import com.example.booking.repository.ReservationRepository;
import com.example.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookingService implements IBookingService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<RoomsAvailabilityDto> getAvailability(LocalDate startDate, LocalDate endDate) {

        List<RoomsAvailabilityDto> roomsAvailability = new ArrayList<>(0);
        List<LocalDate> unavailableDates = new ArrayList<>(0);

        var freeRooms = roomRepository.findAllAvailableRooms();



        freeRooms.forEach(r -> {
            unavailableDates.addAll(reservationRepository.findUnavailableDates(r.getId()));

        });


        return null;
    }

    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto body) {
        return null;
    }

    @Override
    public ReservationResponseDto modifyReservation(ReservationRequestDto body) {
        return null;
    }

    @Override
    public ReservationResponseDto deleteReservation(ReservationRequestDto body) {
        return null;
    }

}
