package com.example.booking.service;

import com.example.booking.dto.AlterationRequestDto;
import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.dto.ReservationResponseDto;
import com.example.booking.enums.ReservationStatus;
import com.example.booking.exception.CheckInDateShouldBeOneDayAfterBookingDateException;
import com.example.booking.exception.NoDatesAvailableException;
import com.example.booking.exception.NoReservationAboveThreeDaysException;
import com.example.booking.exception.ReservationDoesNotExistException;
import com.example.booking.mapper.ReservationMapper;
import com.example.booking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.booking.mapper.ReservationMapper.entityToDto;
import static com.example.booking.utils.BookingUtils.*;
import static com.example.booking.utils.RepositoryUtils.getCustomer;
import static com.example.booking.utils.RepositoryUtils.getRoom;

@Service
@Transactional
public class BookingService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<LocalDate> getAvailability(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) throws
            NoDatesAvailableException {

        var reservedPeriods = reservationRepository.findUnavailableDates(roomId);

        var periodToBeBooked = getDatesBetween(checkInDate, checkOutDate);

        List<LocalDate> availableDates = new ArrayList<>(0);

        reservedPeriods.forEach(reservedPeriod -> {
            var unavailableDates = getDatesBetween(reservedPeriod.getCheckInDate(), reservedPeriod.getCheckOutDate());
            periodToBeBooked.forEach(date -> {
                if (!unavailableDates.contains(date)) {
                    availableDates.add(date);
                }
            });
        });

        if (availableDates.isEmpty()) {
            throw new NoDatesAvailableException();
        }

        return availableDates;
    }

    public ReservationResponseDto createReservation(ReservationRequestDto body) throws NoDatesAvailableException,
            NoReservationAboveThreeDaysException, CheckInDateShouldBeOneDayAfterBookingDateException {

        checkBookingRestrictions(body.getCheckInDate(), body.getCheckOutDate());

        checkIfPeriodIsAvailable(body.getCheckInDate(), body.getCheckOutDate(), body.getRoomId());

        var room = getRoom(body.getRoomId());

        var customer = getCustomer(body.getCustomer().getId());

        var reservation = ReservationMapper.dtoToEntity(body, room);

        reservationRepository.saveAndFlush(reservation);

        return entityToDto(reservation, customer);
    }

    public ReservationResponseDto modifyReservation(AlterationRequestDto body) throws NoDatesAvailableException,
            NoReservationAboveThreeDaysException, CheckInDateShouldBeOneDayAfterBookingDateException {

        checkBookingRestrictions(body.getCheckInDate(), body.getCheckOutDate());

        var reservation = reservationRepository.getById(body.getReservationId());

        checkIfPeriodIsAvailable(body.getCheckInDate(), body.getCheckOutDate(), reservation.getRoomId());

        var room = getRoom(reservation.getRoomId());

        reservation = ReservationMapper.alterationRequestDtoToEntity(body, reservation, room.getPrice());

        reservationRepository.saveAndFlush(reservation);

        return entityToDto(reservation, getCustomer(reservation.getCustomerId()));
    }

    public ReservationResponseDto cancelReservation(Long reservationId) throws ReservationDoesNotExistException {

        var reservation = reservationRepository.getById(reservationId);

        if (reservation != null) {
            reservation.setStatus(ReservationStatus.CANCELED);
            reservationRepository.saveAndFlush(reservation);
        } else {
            throw new ReservationDoesNotExistException(reservationId);
        }

        return entityToDto(reservation, getCustomer(reservation.getCustomerId()));
    }

}
