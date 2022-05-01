package com.hotel.booking.controller;

import com.hotel.booking.dto.*;
import com.hotel.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/reservation")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/check-availability/{roomId}/{checkInDate}/{checkOutDate}")
    @ResponseStatus(HttpStatus.OK)
    public List<AvailabilityDto> checkAvailability(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInDate,
                                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOutDate,
                                                   @RequestParam(required = false) Long roomId) {
        return bookingService.getAvailability(checkInDate, checkOutDate, roomId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDto create(@Valid @RequestBody ReservationRequestDto body) {
        return bookingService.create(body);
    }

    @PutMapping("/modify")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDto modify(@Valid @RequestBody AlterationRequestDto body) {
        return bookingService.modify(body);
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDto cancel(@Valid @RequestBody CancellationRequestDto body) {
        return bookingService.cancel(body);
    }

}
