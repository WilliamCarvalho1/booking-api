package com.example.booking.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingUtils {

    public static String getFormattedDate() {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.now().format(formatter);
    }

}
