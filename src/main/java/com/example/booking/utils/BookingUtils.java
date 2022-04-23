package com.example.booking.utils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingUtils {

    private BookingUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getFormattedDate() {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.now().format(formatter);
    }

    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate).toList();
    }

    public static BigDecimal getTotalValue(BigDecimal roomPrice, LocalDate checkInDate, LocalDate checkOutDate) {

        var numberOfDays = Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays();

        return roomPrice.multiply(BigDecimal.valueOf(numberOfDays));
    }

}
