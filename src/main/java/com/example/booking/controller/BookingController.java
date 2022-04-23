package com.example.booking.controller;

import com.example.booking.dto.AlterationRequestDto;
import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.dto.ReservationResponseDto;
import com.example.booking.exception.CheckInDateShouldBeOneDayAfterBookingDateException;
import com.example.booking.exception.NoDatesAvailableException;
import com.example.booking.exception.NoReservationAboveThreeDaysException;
import com.example.booking.exception.ReservationDoesNotExistException;
import com.example.booking.repository.ReservationRepository;
import com.example.booking.repository.RoomRepository;
import com.example.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ReservationRepository reservationRepository;

    @GetMapping("/checkInDate/{checkInDate}/checkOutDate/{checkOutDate}/roomId/{roomId}")
    public ResponseEntity<List<LocalDate>> getAvailability(@PathVariable LocalDate checkInDate, LocalDate checkOutDate,
                                                           Long roomId) throws NoDatesAvailableException {
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
            throws ReservationDoesNotExistException {
        return new ResponseEntity<>(bookingService.cancelReservation(reservationId), HttpStatus.OK);
    }

}
