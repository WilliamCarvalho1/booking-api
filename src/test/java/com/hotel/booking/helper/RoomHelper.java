package com.hotel.booking.helper;

import com.hotel.booking.model.Room;

import java.math.BigDecimal;

public class RoomHelper {

    public static Room getRoom() {
        return Room.builder()
                .id(1L)
                .floor(1)
                .type("single")
                .price(BigDecimal.valueOf(100))
                .blocked(false)
                .build();
    }

}
