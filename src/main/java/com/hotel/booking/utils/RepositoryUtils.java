package com.hotel.booking.utils;

import com.hotel.booking.model.Customer;
import com.hotel.booking.model.Room;
import com.hotel.booking.repository.CustomerRepository;
import com.hotel.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUtils {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer doesn't exist"));
    }

    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room is not available"));
    }

}
