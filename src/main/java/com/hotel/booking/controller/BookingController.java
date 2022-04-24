package com.hotel.booking.controller;

import com.hotel.booking.dto.AlterationRequestDto;
import com.hotel.booking.dto.ReservationRequestDto;
import com.hotel.booking.dto.ReservationResponseDto;
import com.hotel.booking.exception.*;
import com.hotel.booking.repository.CustomerRepository;
import com.hotel.booking.repository.ReservationRepository;
import com.hotel.booking.repository.RoomRepository;
import com.hotel.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/reservation")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/checkInDate/{checkInDate}/checkOutDate/{checkOutDate}/roomId/{roomId}")
    public ResponseEntity<List<LocalDate>> getAvailability(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInDate,
                                                           @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOutDate,
                                                           @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Long roomId)
            throws NoDatesAvailableException, CheckInDateShouldBeOneDayAfterBookingDateException {
        return new ResponseEntity<>(bookingService.getAvailability(checkInDate, checkOutDate, roomId), HttpStatus.OK);
    }

    @PostMapping("/reserve-room")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto body)
            throws NoDatesAvailableException, NoReservationAboveThreeDaysException,
            CheckInDateShouldBeOneDayAfterBookingDateException {
        return new ResponseEntity<>(bookingService.createReservation(body), HttpStatus.OK);
    }

    @PutMapping("/modify-reservation")
    public ResponseEntity<ReservationResponseDto> modifyReservation(@RequestBody AlterationRequestDto body)
            throws NoDatesAvailableException, NoReservationAboveThreeDaysException,
            CheckInDateShouldBeOneDayAfterBookingDateException {
        return new ResponseEntity<>(bookingService.modifyReservation(body), HttpStatus.OK);
    }

    @PutMapping("/cancel-reservation")
    public ResponseEntity<ReservationResponseDto> cancelReservation(@RequestBody Long reservationId)
            throws ReservationDoesNotExistException, ReservationIsNotActiveException {
        return new ResponseEntity<>(bookingService.cancelReservation(reservationId), HttpStatus.OK);
    }

}
