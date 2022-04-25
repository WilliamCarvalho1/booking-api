package com.hotel.booking.mapper;

import com.hotel.booking.dto.CheckAvailabilityResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckAvailabilityMapper {

    public static CheckAvailabilityResponseDto availableDatesToDto(List<LocalDate> dates) {
        return CheckAvailabilityResponseDto.builder()
                .availableDates(dates)
                .build();
    }

}
