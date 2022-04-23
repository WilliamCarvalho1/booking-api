package com.example.booking.utils;

import com.example.booking.model.Customer;
import com.example.booking.model.Room;
import com.example.booking.repository.CustomerRepository;
import com.example.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RepositoryUtils {

    @Autowired
    private static RoomRepository roomRepository;

    @Autowired
    private static CustomerRepository customerRepository;

    private RepositoryUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer doesn't exist"));
    }

    public static Room getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room is not available"));
    }

}
