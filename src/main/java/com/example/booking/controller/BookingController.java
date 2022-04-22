package com.example.booking.controller;

import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.dto.ReservationResponseDto;
import com.example.booking.dto.RoomsAvailabilityDto;
import com.example.booking.model.Reservation;
import com.example.booking.model.Room;
import com.example.booking.repository.ReservationRepository;
import com.example.booking.repository.RoomRepository;
import com.example.booking.service.IBookingService;
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
    private IBookingService bookingService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<List<RoomsAvailabilityDto>> getAvailability(@PathVariable LocalDate startDate, LocalDate endDate) {
        return new ResponseEntity<>(bookingService.getAvailability(startDate, endDate), HttpStatus.OK);
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
            return ResponseEntity.ok(reservation);
        }

        return ResponseEntity.notFound().build();
    }

}
