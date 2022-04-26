package com.hotel.booking.controller;

import com.hotel.booking.dto.AlterationRequestDto;
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
import java.util.ArrayList;
import java.util.List;

import static com.hotel.booking.helper.AlterationDtoHelper.getAlterationRequestDto;
import static com.hotel.booking.helper.ReservationDtoHelper.*;
import static com.hotel.booking.mapper.CheckAvailabilityMapper.availableDatesToDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

        List<LocalDate> dates = new ArrayList<>(0);
        dates.add(checkInDate);
        dates.add(checkOutDate);

        var responseMock = availableDatesToDto(dates);

        when(service.getRoomAvailability(any(LocalDate.class), any(LocalDate.class), any(Long.class)))
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
        var responseMock = getReservationResponseDto();

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