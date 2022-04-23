package com.example.booking.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingUtils {

    public static String getFormattedDate() {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.now().format(formatter);
    }

    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate).toList();
    }

}
