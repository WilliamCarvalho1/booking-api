package com.hotel.booking.dto;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CancellationRequestDto {

    private Long reservationId;

}
