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
public class ReservationRequestDto {

    @NotNull(message = "Customer Id is a mandatory field")
    private Long customerId;

    @NotNull(message = "Check-in date is a mandatory field")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is a mandatory field")
    private LocalDate checkOutDate;

    @NotNull(message = "Room Id is a mandatory field")
    private Long roomId;

}
