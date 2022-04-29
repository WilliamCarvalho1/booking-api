package com.hotel.booking.exception.handler;

import com.hotel.booking.exception.dto.RequestErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<RequestErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        List<RequestErrorDto> errorsDto = new ArrayList<>(0);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        fieldErrors.forEach(x -> {
            String message = messageSource.getMessage(x, LocaleContextHolder.getLocale());
            RequestErrorDto error = new RequestErrorDto(x.getField(), message);
            errorsDto.add(error);
        });
        return errorsDto;
    }

}
