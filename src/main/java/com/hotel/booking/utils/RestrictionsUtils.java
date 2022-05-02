package com.hotel.booking.utils;

import com.hotel.booking.exception.DateNotAvailableException;
import com.hotel.booking.exception.InvalidCheckInDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class RestrictionsUtils {

    @Autowired
    private BookingUtils bookingUtils;

    private void checkIfPeriodIsAvailable(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {

        var rooms = bookingUtils.getRooms(roomId);

        var availableDates = bookingUtils.getAvailableDates(rooms, checkInDate, checkOutDate);

        if (availableDates.isEmpty()) {
            throw new DateNotAvailableException("No dates available for this period.");
        }
    }

    private void checkIfPeriodIsMoreThanThreeDays(LocalDate checkInDate, LocalDate checkOutDate) {

        if (Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays() > 3) {
            throw new InvalidCheckInDateException("Rooms cannot be reserved for more than 3 days.");
        }
    }

    public void checkRestrictions(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {

        checkBookingDatesRestrictions(checkInDate, checkOutDate);
        checkIfPeriodIsAvailable(checkInDate, checkOutDate, roomId);
        checkIfPeriodIsMoreThanThreeDays(checkInDate, checkOutDate);
    }

    public void checkBookingDatesRestrictions(LocalDate checkInDate, LocalDate checkOutDate) {

        var today = LocalDate.now();

        if (today.equals(checkInDate) || today.isAfter(checkInDate)) {
            throw new InvalidCheckInDateException("Check-in date should be at least one day after the booking day.");
        }
        if (checkInDate.isAfter(today.plusDays(30L))) {
            throw new InvalidCheckInDateException("Check-in date should not be scheduled more than 30 days in advance.");
        }
        if (checkInDate.equals(checkOutDate) || checkInDate.isAfter(checkOutDate)) {
            throw new InvalidCheckInDateException("Check-in date should not be equal or lesser than check-out date.");
        }
    }

}
