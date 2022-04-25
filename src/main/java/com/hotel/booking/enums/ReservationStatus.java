package com.hotel.booking.enums;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    ACTIVE("active"),
    ALTERED("altered"),
    CANCELED("canceled"),
    FINISHED("finished");

    private final String status;

    ReservationStatus(String status) {
        this.status = status;
    }

}
