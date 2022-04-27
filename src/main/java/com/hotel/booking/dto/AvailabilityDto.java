package com.hotel.booking.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class AvailabilityDto {

    Long roomId;
    List<LocalDate> dates;

}
