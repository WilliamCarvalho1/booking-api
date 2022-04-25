package com.hotel.booking.service;

import com.hotel.booking.dto.*;
import com.hotel.booking.enums.ReservationStatus;
import com.hotel.booking.exception.DateNotAvailableException;
import com.hotel.booking.exception.ReservationDoesNotExistException;
import com.hotel.booking.mapper.AlterationMapper;
import com.hotel.booking.mapper.ReservationMapper;
import com.hotel.booking.projections.StartAndEndDates;
import com.hotel.booking.utils.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.hotel.booking.utils.BookingUtils.*;

@Service
@Transactional
public class BookingService {

    @Autowired
    private RepositoryUtils repositoryUtils;

    public CheckAvailabilityResponseDto getRoomAvailability(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {

        checkBookingDatesRestrictions(checkInDate, checkOutDate);

        List<StartAndEndDates> reservedDates = repositoryUtils.findUnavailableDates(roomId);

        var unavailableDates = getUnavailablePeriods(reservedDates);

        var desiredPeriod = getPeriod(checkInDate, checkOutDate);

        return getAvailableDates(unavailableDates, desiredPeriod);
    }

    public ReservationResponseDto create(ReservationRequestDto body) {

        // Just to check if customer and room exist
        repositoryUtils.getCustomer(body.getCustomerId());
        repositoryUtils.getRoom(body.getRoomId());

        checkBookingDatesRestrictions(body.getCheckInDate(), body.getCheckOutDate());
        checkIfPeriodIsMoreThanThreeDays(body.getCheckInDate(), body.getCheckOutDate());

        checkIfPeriodIsAvailable(body.getCheckInDate(), body.getCheckOutDate(), body.getRoomId());

        var room = repositoryUtils.getRoom(body.getRoomId());

        var customer = repositoryUtils.getCustomer(body.getCustomerId());

        var reservation = ReservationMapper.dtoToEntity(body, room);

        repositoryUtils.save(reservation);

        return ReservationMapper.entityToDto(reservation, customer);
    }

    public ReservationResponseDto modify(AlterationRequestDto body) {

        checkBookingDatesRestrictions(body.getCheckInDate(), body.getCheckOutDate());
        checkIfPeriodIsMoreThanThreeDays(body.getCheckInDate(), body.getCheckOutDate());

        var reservation = repositoryUtils.getReservation(body.getReservationId());

        checkIfPeriodIsAvailable(body.getCheckInDate(), body.getCheckOutDate(), reservation.getRoomId());

        var room = repositoryUtils.getRoom(reservation.getRoomId());

        reservation = AlterationMapper.alterationRequestDtoToEntity(body, reservation, room.getPrice());

        repositoryUtils.save(reservation);

        return ReservationMapper.entityToDto(reservation, repositoryUtils.getCustomer(reservation.getCustomerId()));
    }

    public ReservationResponseDto cancel(CancellationRequestDto body) {

        var reservationId = body.getReservationId();

        var reservation = repositoryUtils.getReservation(reservationId);

        if (!reservation.getStatus().equals(ReservationStatus.CANCELED.getStatus())
                && !reservation.getStatus().equals(ReservationStatus.FINISHED.getStatus())) {
            reservation.setStatus(ReservationStatus.CANCELED.getStatus());
            repositoryUtils.save(reservation);
        } else {
            throw new ReservationDoesNotExistException("Reservation " + reservationId + " does not exist");
        }

        return ReservationMapper.entityToDto(reservation, repositoryUtils.getCustomer(reservation.getCustomerId()));
    }

    private void checkIfPeriodIsAvailable(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {

        var availableDates = getRoomAvailability(checkInDate, checkOutDate, roomId);
        var bookingPeriod = getPeriod(checkInDate, checkOutDate);

        bookingPeriod.forEach(date -> {
            if (!availableDates.getAvailableDates().contains(date)) {
                throw new DateNotAvailableException(date + " is already booked.");
            }
        });
    }

}
