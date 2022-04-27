package com.hotel.booking.service;

import com.hotel.booking.dto.*;
import com.hotel.booking.enums.ReservationStatus;
import com.hotel.booking.exception.CustomerDoesNotMatchException;
import com.hotel.booking.exception.DateNotAvailableException;
import com.hotel.booking.exception.ReservationDoesNotExistException;
import com.hotel.booking.mapper.ReservationMapper;
import com.hotel.booking.utils.BookingUtils;
import com.hotel.booking.utils.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.hotel.booking.mapper.AlterationMapper.alterationRequestDtoToEntity;

@Service
@Transactional
public class BookingService {

    @Autowired
    private RepositoryUtils repositoryUtils;

    @Autowired
    private BookingUtils bookingUtils;

    public List<AvailabilityDto> getRoomAvailability(LocalDate checkInDate, LocalDate checkOutDate) {

        var rooms = repositoryUtils.getRooms();

        bookingUtils.checkBookingDatesRestrictions(checkInDate, checkOutDate);

        var bookedDates = repositoryUtils.findUnavailableDates();

        var unavailablePeriods = bookingUtils.getUnavailablePeriods(bookedDates, rooms);

        var desiredPeriod = bookingUtils.getPeriod(checkInDate, checkOutDate);

        return bookingUtils.getAvailableDates(unavailablePeriods, desiredPeriod);
    }

    public ReservationResponseDto create(ReservationRequestDto body) {

        bookingUtils.checkBookingDatesRestrictions(body.getCheckInDate(), body.getCheckOutDate());
        bookingUtils.checkIfPeriodIsMoreThanThreeDays(body.getCheckInDate(), body.getCheckOutDate());

        checkIfPeriodIsAvailable(body.getCheckInDate(), body.getCheckOutDate());

        var room = repositoryUtils.getRoom(body.getRoomId());

        var customer = repositoryUtils.getCustomer(body.getCustomerId());

        var reservation = ReservationMapper.dtoToEntity(body, room);

        repositoryUtils.save(reservation);

        return ReservationMapper.entityToDto(reservation, customer);
    }

    public ReservationResponseDto modify(AlterationRequestDto body) {

        bookingUtils.checkBookingDatesRestrictions(body.getCheckInDate(), body.getCheckOutDate());
        bookingUtils.checkIfPeriodIsMoreThanThreeDays(body.getCheckInDate(), body.getCheckOutDate());

        var reservation = repositoryUtils.getReservation(body.getReservationId());

        var customer = repositoryUtils.getCustomer(body.getCustomerId());

        if (!Objects.equals(reservation.getCustomerId(), customer.getId())) {
            throw new CustomerDoesNotMatchException("Provided customer does not match the reservation's customer.");
        }

        var room = repositoryUtils.getRoom(body.getRoomId());

        checkIfPeriodIsAvailable(body.getCheckInDate(), body.getCheckOutDate());

        reservation = alterationRequestDtoToEntity(body, reservation, room);

        repositoryUtils.save(reservation);

        return ReservationMapper.entityToDto(reservation, customer);
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

    private void checkIfPeriodIsAvailable(LocalDate checkInDate, LocalDate checkOutDate) {

        var availableDates = getRoomAvailability(checkInDate, checkOutDate);
        var bookingPeriod = bookingUtils.getPeriod(checkInDate, checkOutDate);

        availableDates.forEach(room -> room.getDates()
                .forEach(availableDate -> {
                    if (!bookingPeriod.contains(availableDate)) {
                        throw new DateNotAvailableException(availableDate + " is already booked.");
                    }
                }));

    }

}
