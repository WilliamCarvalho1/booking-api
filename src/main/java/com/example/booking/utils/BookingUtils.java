package com.example.booking.utils;

import com.example.booking.exception.CheckInDateShouldBeOneDayAfterBookingDateException;
import com.example.booking.exception.NoDatesAvailableException;
import com.example.booking.exception.NoReservationAboveThreeDaysException;
import com.example.booking.service.BookingService;
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

    public static List<LocalDate> getDatesBetween(LocalDate checkInDate, LocalDate checkOutDate) {
        return checkInDate.datesUntil(checkOutDate).toList();
    }

    public static BigDecimal getTotalValue(BigDecimal roomPrice, LocalDate checkInDate, LocalDate checkOutDate) {

        var numberOfDays = Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays();

        return roomPrice.multiply(BigDecimal.valueOf(numberOfDays));
    }

    public static void checkBookingRestrictions(LocalDate checkInDate, LocalDate checkOutDate) throws NoReservationAboveThreeDaysException, CheckInDateShouldBeOneDayAfterBookingDateException {

        var bookingDay = LocalDate.now();

        if (checkInDate.isBefore(bookingDay) || checkInDate.isEqual(bookingDay)) {
            throw new CheckInDateShouldBeOneDayAfterBookingDateException(bookingDay);
        }
        if (Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays() > 3) {
            throw new NoReservationAboveThreeDaysException();
        }
    }

    public static void checkIfPeriodIsAvailable(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) throws NoDatesAvailableException {

        var availableDates = bookingService.getAvailability(checkInDate, checkOutDate, roomId);
        var bookingPeriod = getDatesBetween(checkInDate, checkOutDate);

        bookingPeriod.forEach(date -> {
            if (!availableDates.contains(date)) {
                throw new RuntimeException(date + "is already booked.");
            }
        });
    }

}
