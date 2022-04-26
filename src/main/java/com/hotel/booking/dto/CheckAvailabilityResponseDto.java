package com.hotel.booking.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class CheckAvailabilityResponseDto {

    List<LocalDate> availableDates;

}
