package com.hotel.booking.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CancellationRequestDto {

    private Long reservationId;

}
