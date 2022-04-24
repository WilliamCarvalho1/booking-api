package com.hotel.booking.utils;

import com.hotel.booking.exception.CheckInDateShouldBeOneDayAfterBookingDateException;
import com.hotel.booking.exception.NoReservationAboveThreeDaysException;
import com.hotel.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingUtils {

    @Autowired
    private static BookingService bookingService;

    private BookingUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getFormattedDate() {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.now().format(formatter);
    }

    public static List<LocalDate> getPeriod(LocalDate checkInDate, LocalDate checkOutDate) {
        return checkInDate.datesUntil(checkOutDate.plusDays(1L)).toList();
    }

    public static BigDecimal getTotalValue(BigDecimal roomPrice, LocalDate checkInDate, LocalDate checkOutDate) {

        var numberOfDays = Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays();

        return roomPrice.multiply(BigDecimal.valueOf(numberOfDays));
    }

    public static void checkBookingRestrictions(LocalDate checkInDate, LocalDate checkOutDate) throws
            NoReservationAboveThreeDaysException, CheckInDateShouldBeOneDayAfterBookingDateException {

        var bookingDay = LocalDate.now();

        if (checkInDate.isBefore(bookingDay) || checkInDate.isEqual(bookingDay)) {
            throw new CheckInDateShouldBeOneDayAfterBookingDateException();
        }
        if (Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays() > 3) {
            throw new NoReservationAboveThreeDaysException();
        }
    }

}
