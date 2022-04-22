package com.example.booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class RoomsAvailabilityDto {

    private RoomDto room;
    private List<LocalDate> availableDates;

}
