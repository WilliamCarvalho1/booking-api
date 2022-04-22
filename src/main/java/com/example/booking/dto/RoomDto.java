package com.example.booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RoomDto {

    private Long id;

    private int floor;

    private String type;

    private BigDecimal price;

    private boolean flagAvailable;

}
