package com.hotel.booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

}
