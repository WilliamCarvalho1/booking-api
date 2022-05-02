package com.hotel.booking.exception.handler;

import com.hotel.booking.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ReservationDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleReservationDoesNotExistException(ReservationDoesNotExistException ex) {
        return ExceptionResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(DateNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleDateNotAvailableException(DateNotAvailableException ex) {
        return ExceptionResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return ExceptionResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleRoomNotAvailableException(RoomNotAvailableException ex) {
        return ExceptionResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(InvalidCheckInDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleInvalidCheckInDateException(InvalidCheckInDateException ex) {
        return ExceptionResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(CustomerDoesNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleCustomerDoesNotMatchException(CustomerDoesNotMatchException ex) {
        return ExceptionResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

}
