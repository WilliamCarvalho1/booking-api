package com.hotel.booking.utils;

import com.hotel.booking.dto.CheckAvailabilityResponseDto;
import com.hotel.booking.exception.DateNotAvailableException;
import com.hotel.booking.exception.InvalidCheckInDateException;
import com.hotel.booking.projections.StartAndEndDates;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.hotel.booking.mapper.CheckAvailabilityMapper.availableDatesToDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingUtils {

    public static List<LocalDate> getPeriod(LocalDate checkInDate, LocalDate checkOutDate) {
        return checkInDate.datesUntil(checkOutDate.plusDays(1L)).toList();
    }

    public static BigDecimal getTotalValue(BigDecimal roomPrice, LocalDate checkInDate, LocalDate checkOutDate) {

        var numberOfDays = Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays();

        return roomPrice.multiply(BigDecimal.valueOf(numberOfDays));
    }

    public static List<LocalDate> getUnavailablePeriods(List<StartAndEndDates> reservedDates) {

        List<LocalDate> unavailableDates = new ArrayList<>(0);

        reservedDates.forEach(reservedDate ->
                unavailableDates.addAll(getPeriod(reservedDate.getCheckInDate(), reservedDate.getCheckOutDate()))
        );

        return unavailableDates;
    }

    public static CheckAvailabilityResponseDto getAvailableDates(List<LocalDate> unavailableDates, List<LocalDate> desiredPeriod) {

        List<LocalDate> availableDates = new ArrayList<>(0);

        desiredPeriod.forEach(desiredDate -> {
            if (!unavailableDates.contains(desiredDate)) {
                availableDates.add(desiredDate);
            }
        });

        if (availableDates.isEmpty()) {
            throw new DateNotAvailableException("No dates available for this period.");
        }

        return availableDatesToDto(availableDates);
    }

    public static void checkBookingDatesRestrictions(LocalDate checkInDate, LocalDate checkOutDate) {

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

    public static void checkIfPeriodIsMoreThanThreeDays(LocalDate checkInDate, LocalDate checkOutDate) {

        if (Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays() > 3) {
            throw new InvalidCheckInDateException("Rooms cannot be reserved for more than 3 days.");
        }
    }

}
