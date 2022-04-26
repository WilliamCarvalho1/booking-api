package com.hotel.booking.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class CancellationRequestDto {

    private Long reservationId;

}
