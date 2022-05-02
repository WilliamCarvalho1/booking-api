package com.hotel.booking.exception.handler;

import com.hotel.booking.exception.*;
import com.hotel.booking.exception.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ReservationDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponseDto handleReservationDoesNotExistException(ReservationDoesNotExistException ex) {
        return ExceptionResponseDto.builder()
                .message(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(DateNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto handleDateNotAvailableException(DateNotAvailableException ex) {
        return ExceptionResponseDto.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponseDto handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return ExceptionResponseDto.builder()
                .message(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponseDto handleRoomNotAvailableException(RoomNotAvailableException ex) {
        return ExceptionResponseDto.builder()
                .message(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(InvalidCheckInDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto handleInvalidCheckInDateException(InvalidCheckInDateException ex) {
        return ExceptionResponseDto.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(CustomerDoesNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto handleCustomerDoesNotMatchException(CustomerDoesNotMatchException ex) {
        return ExceptionResponseDto.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

}
