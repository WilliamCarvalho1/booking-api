package com.hotel.booking.helper;

import com.hotel.booking.dto.AvailabilityDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityDtoHelper {

    public static List<AvailabilityDto> getAvailabilityDto(LocalDate checkInDate, LocalDate checkOutDate) {

        List<AvailabilityDto> availabilityList = new ArrayList<>(0);
        availabilityList.add(AvailabilityDto.builder()
                .roomId(1L)
                .dates(checkInDate.datesUntil(checkOutDate.plusDays(1L)).toList())
                .build());

        return availabilityList;
    }

}
