package com.hotel.booking.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CancellationRequestDto {

    @NotNull(message = "Reservation Id is a mandatory field")
    private Long reservationId;

}
