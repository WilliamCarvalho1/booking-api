package com.hotel.booking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {

    ACTIVE("active"),
    ALTERED("altered"),
    CANCELED("canceled"),
    FINISHED("finished");

    private final String status;

}
