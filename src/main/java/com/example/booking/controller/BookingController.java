package com.example.booking.controller;

import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.dto.ReservationResponseDto;
import com.example.booking.model.Reservation;
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
    public ResponseEntity<List<LocalDate>> getAvailability(@PathVariable LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {
        return new ResponseEntity<>(bookingService.getAvailability(checkInDate, checkOutDate, roomId), HttpStatus.OK);
    }

    @PostMapping("/reserve-room")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto body) {
        return new ResponseEntity<>(bookingService.createReservation(body), HttpStatus.OK);
    }

    @PutMapping("/modify-reservation")
    public ResponseEntity<ReservationResponseDto> modifyReservation(@RequestBody ReservationRequestDto body) {
        return new ResponseEntity<>(bookingService.modifyReservation(body), HttpStatus.OK);
    }

    @DeleteMapping("/cancel-reservation")
    public ResponseEntity<Reservation> cancelReservation(@RequestBody Long reservationId) {
        var reservation = reservationRepository.findById(reservationId);

        if (reservation != null) {
            reservationRepository.delete(reservationId);
            return ResponseEntity.ok(reservation.orElseThrow(() -> new RuntimeException("Reservation doesn't exist")));
        }

        return ResponseEntity.notFound().build();
    }

}
