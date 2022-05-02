package com.hotel.booking.utils;

import com.hotel.booking.dto.AvailabilityDto;
import com.hotel.booking.exception.InvalidCheckInDateException;
import com.hotel.booking.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BookingUtils {

    @Autowired
    private RepositoryUtils repositoryUtils;

    public List<LocalDate> getPeriod(LocalDate checkInDate, LocalDate checkOutDate) {

        return checkInDate.datesUntil(checkOutDate.plusDays(1L)).toList();
    }

    public static BigDecimal getTotalValue(BigDecimal roomPrice, LocalDate checkInDate, LocalDate checkOutDate) {

        var numberOfDays = Duration.between(checkInDate.atStartOfDay(), checkOutDate.atStartOfDay()).toDays();

        return roomPrice.multiply(BigDecimal.valueOf(numberOfDays));
    }

    public List<Room> getRooms(Long roomId) {

        List<Room> rooms = new ArrayList<>(0);

        if (roomId == null) {
            rooms.addAll(repositoryUtils.getRooms());
        } else {
            rooms.add(repositoryUtils.getRoom(roomId));
        }
        return rooms;
    }

    public List<AvailabilityDto> getUnavailableDates(List<Room> rooms) {

        List<AvailabilityDto> unavailableDatesForEachRoomList = new ArrayList<>(0);

        List<LocalDate> dates = new ArrayList<>(0);

        var roomsBookedDates = repositoryUtils.findUnavailableDates();

        rooms.forEach(room -> {
            roomsBookedDates.stream()
                    .filter(rbd -> Objects.equals(room.getId(), rbd.getRoomId()))
                    .forEach(rbd -> dates.addAll(getPeriod(rbd.getCheckInDate(), rbd.getCheckOutDate())));
            unavailableDatesForEachRoomList.add(AvailabilityDto.builder()
                    .roomId(room.getId())
                    .dates(List.copyOf(dates))
                    .build()
            );
            dates.clear();
        });

        return unavailableDatesForEachRoomList;
    }

    public List<AvailabilityDto> getAvailableDates(List<Room> rooms, LocalDate checkInDate, LocalDate checkOutDate) {

        var unavailableDatesForEachRoomList = getUnavailableDates(rooms);

        var desiredDates = getPeriod(checkInDate, checkOutDate);

        List<AvailabilityDto> availableDatesForEachRoomList = new ArrayList<>(0);

        List<LocalDate> dates = new ArrayList<>(0);

        unavailableDatesForEachRoomList.forEach(room -> {
            desiredDates.stream()
                    .filter(date -> !room.getDates().contains(date))
                    .forEach(dates::add);
            if (!dates.isEmpty()) {
                availableDatesForEachRoomList.add(AvailabilityDto.builder()
                        .roomId(room.getRoomId())
                        .dates(List.copyOf(dates))
                        .build());
            }
            dates.clear();
        });

        return availableDatesForEachRoomList;
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
