package com.hotel.booking.helper;

import com.hotel.booking.model.Customer;

public class CustomerHelper {

    public static Customer getCustomer() {

        return Customer.builder()
                .id(1L)
                .firstName("Michael")
                .lastName("Jordan")
                .phone("1234-1234")
                .email("michael.jordan@email.com")
                .build();
    }

}
