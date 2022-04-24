package com.hotel.booking.service;

import com.hotel.booking.dto.AlterationRequestDto;
import com.hotel.booking.dto.ReservationRequestDto;
import com.hotel.booking.dto.ReservationResponseDto;
import com.hotel.booking.enums.ReservationStatus;
import com.hotel.booking.exception.*;
import com.hotel.booking.mapper.ReservationMapper;
import com.hotel.booking.model.Reservation;
import com.hotel.booking.projections.StartAndEndDates;
import com.hotel.booking.repository.ReservationRepository;
import com.hotel.booking.utils.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.hotel.booking.utils.BookingUtils.checkBookingRestrictions;
import static com.hotel.booking.utils.BookingUtils.getPeriod;

@Service
@Transactional
public class BookingService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RepositoryUtils repositoryUtils;

    public List<LocalDate> getAvailability(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) throws
            NoDatesAvailableException, CheckInDateShouldBeOneDayAfterBookingDateException {

        var today = LocalDate.now();

        if (today.equals(checkInDate) || today.isAfter(checkInDate)) {
            throw new CheckInDateShouldBeOneDayAfterBookingDateException();
        }

        var reservedDates = reservationRepository.findUnavailableDates(roomId);

        var unavailableDates = getUnavailableDates(reservedDates);

        var desiredPeriod = getPeriod(checkInDate, checkOutDate);

        return getAvailableDates(unavailableDates, desiredPeriod);
    }

    private List<LocalDate> getAvailableDates(List<LocalDate> unavailableDates, List<LocalDate> desiredPeriod) throws NoDatesAvailableException {

        List<LocalDate> availableDates = new ArrayList<>(0);

        desiredPeriod.forEach(desiredDate -> {
            if (!unavailableDates.contains(desiredDate)) {
                availableDates.add(desiredDate);
            }
        });

        if (availableDates.isEmpty()) {
            throw new NoDatesAvailableException();
        }

        return availableDates;
    }

    private List<LocalDate> getUnavailableDates(List<StartAndEndDates> reservedDates) {

        List<LocalDate> unavailableDates = new ArrayList<>(0);

        reservedDates.forEach(reservedDate ->
                unavailableDates.addAll(getPeriod(reservedDate.getCheckInDate(), reservedDate.getCheckOutDate()))
        );

        return unavailableDates;
    }

    public ReservationResponseDto createReservation(ReservationRequestDto body) throws NoDatesAvailableException,
            NoReservationAboveThreeDaysException, CheckInDateShouldBeOneDayAfterBookingDateException {

        checkBookingRestrictions(body.getCheckInDate(), body.getCheckOutDate());

        checkIfPeriodIsAvailable(body.getCheckInDate(), body.getCheckOutDate(), body.getRoomId());

        var room = repositoryUtils.getRoom(body.getRoomId());

        var customer = repositoryUtils.getCustomer(body.getCustomerId());

        var reservation = ReservationMapper.dtoToEntity(body, room);

        reservationRepository.saveAndFlush(reservation);

        return ReservationMapper.entityToDto(reservation, customer);
    }

    public ReservationResponseDto modifyReservation(AlterationRequestDto body) throws NoDatesAvailableException,
            NoReservationAboveThreeDaysException, CheckInDateShouldBeOneDayAfterBookingDateException {

        checkBookingRestrictions(body.getCheckInDate(), body.getCheckOutDate());

        var reservation = reservationRepository.getById(body.getReservationId());

        checkIfPeriodIsAvailable(body.getCheckInDate(), body.getCheckOutDate(), reservation.getRoomId());

        var room = repositoryUtils.getRoom(reservation.getRoomId());

        reservation = ReservationMapper.alterationRequestDtoToEntity(body, reservation, room.getPrice());

        reservationRepository.saveAndFlush(reservation);

        return ReservationMapper.entityToDto(reservation, repositoryUtils.getCustomer(reservation.getCustomerId()));
    }

    public ReservationResponseDto cancelReservation(Long reservationId) throws ReservationDoesNotExistException, ReservationIsNotActiveException {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationDoesNotExistException::new);

        if (!reservation.getStatus().equals(ReservationStatus.CANCELED.getReservationStatus())
                && !reservation.getStatus().equals(ReservationStatus.FINISHED.getReservationStatus())) {
            reservation.setStatus(ReservationStatus.CANCELED.getReservationStatus());
            reservationRepository.saveAndFlush(reservation);
        } else {
            throw new ReservationIsNotActiveException();
        }

        return ReservationMapper.entityToDto(reservation, repositoryUtils.getCustomer(reservation.getCustomerId()));
    }

    private void checkIfPeriodIsAvailable(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) throws NoDatesAvailableException, CheckInDateShouldBeOneDayAfterBookingDateException {

        var availableDates = getAvailability(checkInDate, checkOutDate, roomId);
        var bookingPeriod = getPeriod(checkInDate, checkOutDate);

        bookingPeriod.forEach(date -> {
            if (!availableDates.contains(date)) {
                throw new RuntimeException(date + "is already booked.");
            }
        });
    }

}
