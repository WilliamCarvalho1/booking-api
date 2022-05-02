package com.hotel.booking.service;

import com.hotel.booking.dto.*;
import com.hotel.booking.enums.ReservationStatus;
import com.hotel.booking.exception.CustomerDoesNotMatchException;
import com.hotel.booking.exception.DateNotAvailableException;
import com.hotel.booking.exception.ReservationDoesNotExistException;
import com.hotel.booking.mapper.ReservationMapper;
import com.hotel.booking.utils.BookingUtils;
import com.hotel.booking.utils.RepositoryUtils;
import com.hotel.booking.utils.RestrictionsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private RestrictionsUtils restrictionsUtils;

    public List<AvailabilityDto> getAvailability(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {

        var rooms = bookingUtils.getRooms(roomId);

        restrictionsUtils.checkBookingDatesRestrictions(checkInDate, checkOutDate);

        var availableDates = bookingUtils.getAvailableDates(rooms, checkInDate, checkOutDate);

        if (availableDates.isEmpty()) {
            throw new DateNotAvailableException("No dates available for this period.");
        }

        return availableDates;
    }

    public ReservationResponseDto create(ReservationRequestDto body) {

        restrictionsUtils.checkRestrictions(body.getCheckInDate(), body.getCheckOutDate(), body.getRoomId());

        var room = repositoryUtils.getRoom(body.getRoomId());
        var customer = repositoryUtils.getCustomer(body.getCustomerId());
        var reservation = ReservationMapper.dtoToEntity(body, room);

        repositoryUtils.save(reservation);

        return ReservationMapper.entityToDto(reservation, customer);
    }

    public ReservationResponseDto modify(AlterationRequestDto body) {

        var reservation = repositoryUtils.getReservation(body.getReservationId());
        var customer = repositoryUtils.getCustomer(body.getCustomerId());
        var room = repositoryUtils.getRoom(body.getRoomId());

        if (!Objects.equals(reservation.getCustomerId(), customer.getId())) {
            throw new CustomerDoesNotMatchException("Provided customer does not match the reservation's customer.");
        }

        var cancellationBody = CancellationRequestDto.builder()
                .reservationId(reservation.getId())
                .build();
        cancel(cancellationBody);

        restrictionsUtils.checkRestrictions(body.getCheckInDate(), body.getCheckOutDate(), body.getRoomId());

        reservation = alterationRequestDtoToEntity(body, customer.getId(), room);

        repositoryUtils.save(reservation);

        return ReservationMapper.entityToDto(reservation, customer);
    }

    public ReservationResponseDto cancel(CancellationRequestDto body) {

        var reservationId = body.getReservationId();

        var reservation = repositoryUtils.getReservation(reservationId);

        if (!reservation.getStatus().equals(ReservationStatus.CANCELED.getStatus()) &&
                !reservation.getStatus().equals(ReservationStatus.FINISHED.getStatus())) {
            reservation.setStatus(ReservationStatus.CANCELED.getStatus());
            reservation.setTotalValue(BigDecimal.ZERO);
            repositoryUtils.save(reservation);
        } else {
            throw new ReservationDoesNotExistException("Reservation " + reservationId + " does not exist");
        }

        return ReservationMapper.entityToDto(reservation, repositoryUtils.getCustomer(reservation.getCustomerId()));
    }

}
