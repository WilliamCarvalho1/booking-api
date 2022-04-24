package com.hotel.booking.advice;

import com.hotel.booking.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    public ResponseEntity<String> handleReservationDoesNotExistException() {
        return new ResponseEntity<>("There is no reservation with this id.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateNotAvailableException.class)
    public ResponseEntity<String> handleDateNotAvailableException() {
        return new ResponseEntity<>("This date is already booked.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationIsNotActiveException.class)
    public ResponseEntity<String> handleReservationIsNotActiveException() {
        return new ResponseEntity<>("This reservation is not active", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckInDateShouldBeOneDayAfterBookingDateException.class)
    public ResponseEntity<String> handleCheckInDateShouldBeOneDayAfterBookingDateException() {
        return new ResponseEntity<>("Check-in date should be at least one day after the booking day.",
                HttpStatus.BAD_REQUEST);
    }

}
