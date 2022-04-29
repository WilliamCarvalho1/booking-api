package com.hotel.booking.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AlterationRequestDto {

    @NotNull(message = "Reservation Id is a mandatory field")
    private Long reservationId;

    @NotNull(message = "Customer Id is a mandatory field")
    private Long customerId;

    @NotNull(message = "Room Id is a mandatory field")
    private Long roomId;

    @NotNull(message = "Check-in date is a mandatory field")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is a mandatory field")
    private LocalDate checkOutDate;

}
