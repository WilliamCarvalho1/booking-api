package com.example.booking.advice;

import com.example.booking.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class BookingControllerAdvice {

    @ExceptionHandler(NoDatesAvailableException.class)
    public ResponseEntity<String> handleNoDatesAvailableException() {
        return new ResponseEntity<>("No dates available for this period.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoReservationAboveThreeDaysException.class)
    public ResponseEntity<String> handleNoReservationAboveThreeDaysException() {
        return new ResponseEntity<>("Rooms cannot be reserved for more than 3 days.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationDoesNotExistException.class)
    public ResponseEntity<String> handleReservationDoesNotExistException(Long id) {
        return new ResponseEntity<>("Reservation with id: " + id + "doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateNotAvailableException.class)
    public ResponseEntity<String> handleDateNotAvailableException(LocalDate date) {
        return new ResponseEntity<>(date + "is already booked.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckInDateShouldBeOneDayAfterBookingDateException.class)
    public ResponseEntity<String> handleCheckInDateShouldBeOneDayAfterBookingDateException(LocalDate date) {
        return new ResponseEntity<>("Check-in date should be at least one day after the booking day: "
                + date, HttpStatus.BAD_REQUEST);
    }

}
