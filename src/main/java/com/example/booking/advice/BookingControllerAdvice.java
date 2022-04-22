package com.example.booking.advice;

import com.example.booking.exception.NoRoomsAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookingControllerAdvice {

    @ExceptionHandler(NoRoomsAvailableException.class)
    public ResponseEntity<String> handleNoSpotException() {
        return new ResponseEntity<>("No rooms available for this date.", HttpStatus.NOT_FOUND);
    }

}
