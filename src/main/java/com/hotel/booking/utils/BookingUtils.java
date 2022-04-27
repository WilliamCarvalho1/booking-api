package com.hotel.booking.utils;

import com.hotel.booking.dto.AvailabilityDto;
import com.hotel.booking.exception.DateNotAvailableException;
import com.hotel.booking.exception.InvalidCheckInDateException;
import com.hotel.booking.model.Room;
import com.hotel.booking.projections.RoomsBookedDates;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BookingUtils {

    public List<LocalDate> getPeriod(LocalDate checkInDate, LocalDate checkOutDate) {
        return checkInDate.datesUntil(checkOutDate.plusDays(1L)).toList();
    }

    public static BigDecimal getTotalValue(BigDecimal roomPrice, LocalDate checkInDate, LocalDate checkOutDate) {

        var numberOfDays = Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays();

        return roomPrice.multiply(BigDecimal.valueOf(numberOfDays));
    }

    public List<AvailabilityDto> getUnavailablePeriods(List<RoomsBookedDates> roomsBookedDates, List<Room> rooms) {

        List<Long> filteredRoomIds = new ArrayList<>(0);

        rooms.forEach(room -> filteredRoomIds.add(room.getId()));

        List<AvailabilityDto> mergedRoomIdsAndDates = new ArrayList<>(0);

        List<LocalDate> dates = new ArrayList<>(0);

        filteredRoomIds.forEach(id -> {
            roomsBookedDates.forEach(room -> {
                if (Objects.equals(id, room.getRoomId())) {
                    dates.addAll(getPeriod(room.getCheckInDate(), room.getCheckOutDate()));
                }
            });
            mergedRoomIdsAndDates.add(AvailabilityDto.builder()
                    .roomId(id)
                    .dates(List.copyOf(dates))
                    .build()
            );
            dates.clear();
        });

        return mergedRoomIdsAndDates;
    }

    public List<AvailabilityDto> getAvailableDates(List<AvailabilityDto> roomsUnavailableDates,
                                                   List<LocalDate> desiredPeriod) {

        List<AvailabilityDto> roomsAvailableDates = new ArrayList<>(0);

        List<LocalDate> dates = new ArrayList<>(0);

        roomsUnavailableDates.forEach(roomsUnavailableDate -> {
            desiredPeriod.forEach(date -> {
                if (!roomsUnavailableDate.getDates().contains(date)) {
                    dates.add(date);
                }
            });
            if (!dates.isEmpty()) {
                roomsAvailableDates.add(AvailabilityDto.builder()
                        .roomId(roomsUnavailableDate.getRoomId())
                        .dates(List.copyOf(dates))
                        .build());
            }
            dates.clear();
        });

        if (roomsAvailableDates.isEmpty()) {
            throw new DateNotAvailableException("No dates available for this period.");
        }

        return roomsAvailableDates;
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

    public void checkIfPeriodIsMoreThanThreeDays(LocalDate checkInDate, LocalDate checkOutDate) {

        if (Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays() > 3) {
            throw new InvalidCheckInDateException("Rooms cannot be reserved for more than 3 days.");
        }
    }

}
