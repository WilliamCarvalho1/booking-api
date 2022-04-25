package com.hotel.booking.utils;

import com.hotel.booking.exception.CustomerNotFoundException;
import com.hotel.booking.exception.ReservationDoesNotExistException;
import com.hotel.booking.exception.RoomNotAvailableException;
import com.hotel.booking.model.Customer;
import com.hotel.booking.model.Reservation;
import com.hotel.booking.model.Room;
import com.hotel.booking.projections.StartAndEndDates;
import com.hotel.booking.repository.CustomerRepository;
import com.hotel.booking.repository.ReservationRepository;
import com.hotel.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryUtils {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
    }

    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotAvailableException("Room is not available or does not exist"));
    }

    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationDoesNotExistException("There is no reservation with id: " + reservationId));
    }

    public void save(Reservation reservation) {
        reservationRepository.saveAndFlush(reservation);
    }

    public List<StartAndEndDates> findUnavailableDates(Long roomId) {
        return reservationRepository.findUnavailableDates(roomId);
    }

}
