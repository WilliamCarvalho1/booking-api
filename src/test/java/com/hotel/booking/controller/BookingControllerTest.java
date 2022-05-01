package com.hotel.booking.controller;

import com.hotel.booking.dto.AlterationRequestDto;
import com.hotel.booking.dto.AvailabilityDto;
import com.hotel.booking.dto.CancellationRequestDto;
import com.hotel.booking.dto.ReservationRequestDto;
import com.hotel.booking.service.BookingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static com.hotel.booking.helper.AlterationDtoHelper.getAlterationRequestDto;
import static com.hotel.booking.helper.AvailabilityDtoHelper.getAvailabilityDto;
import static com.hotel.booking.helper.ReservationDtoHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for BookingController")
class BookingControllerTest {

    @InjectMocks
    private BookingController controller;

    @Mock
    private BookingService service;

    @Test
    @DisplayName("Should check reservation availability")
    void shouldCheckAvailability() {

        var checkInDate = LocalDate.parse("2022-05-08");
        var checkOutDate = LocalDate.parse("2022-05-10");

        List<AvailabilityDto> responseMock = getAvailabilityDto(checkInDate, checkOutDate);

        when(service.getAvailability(any(LocalDate.class), any(LocalDate.class), anyLong()))
                .thenReturn(responseMock);

        var response = controller.checkAvailability(checkInDate, checkOutDate, 1L);

        assertEquals(responseMock, response);
    }

    @Test
    @DisplayName("Should create a reservation")
    void shouldCreateReservation() {

        var requestMock = getReservationRequestDto();
        var responseMock = getReservationResponseDto();

        when(service.create(any(ReservationRequestDto.class)))
                .thenReturn(responseMock);

        var response = controller.create(requestMock);

        assertEquals(responseMock, response);
    }

    @Test
    @DisplayName("Should modify a reservation")
    void shouldModifyReservation() {

        var requestMock = getAlterationRequestDto();
        var responseMock = getModifiedReservationResponseDto();

        when(service.modify(any(AlterationRequestDto.class)))
                .thenReturn(responseMock);

        var response = controller.modify(requestMock);

        assertEquals(responseMock, response);
    }

    @Test
    @DisplayName("Should cancel a reservation")
    void shouldCancelReservation() {

        var requestMock = CancellationRequestDto.builder()
                .reservationId(1L)
                .build();

        var responseMock = getCanceledReservationResponseDto();

        when(service.cancel(any(CancellationRequestDto.class)))
                .thenReturn(responseMock);

        var response = controller.cancel(requestMock);

        assertEquals(responseMock, response);
    }

}