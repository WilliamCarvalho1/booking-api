package com.hotel.booking.enums;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    ACTIVE("active"),
    ALTERED("altered"),
    CANCELED("canceled"),
    FINISHED("finished");

    private String reservationStatus;

    ReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

}
