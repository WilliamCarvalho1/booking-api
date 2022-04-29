package com.hotel.booking.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestErrorDto {

    private String field;
    private String error;

}
