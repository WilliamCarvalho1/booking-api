package com.hotel.booking.exception.handler;

import com.hotel.booking.exception.dto.ValidationErrorResponseDto;
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
    public List<ValidationErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        List<ValidationErrorResponseDto> validationErrorsDto = new ArrayList<>(0);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        fieldErrors.forEach(x -> {
            String message = messageSource.getMessage(x, LocaleContextHolder.getLocale());
            ValidationErrorResponseDto error = new ValidationErrorResponseDto(x.getField(), message);
            validationErrorsDto.add(error);
        });

        return validationErrorsDto;
    }

}
