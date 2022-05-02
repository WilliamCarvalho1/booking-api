package com.hotel.booking.service;

import com.hotel.booking.dto.AvailabilityDto;
import com.hotel.booking.dto.CancellationRequestDto;
import com.hotel.booking.helper.AvailabilityDtoHelper;
import com.hotel.booking.helper.CustomerHelper;
import com.hotel.booking.helper.RoomHelper;
import com.hotel.booking.utils.BookingUtils;
import com.hotel.booking.utils.RepositoryUtils;
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
import static com.hotel.booking.helper.ReservationHelper.getReservation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for BookingService")
class BookingServiceTest {

    @InjectMocks
    private BookingService service;

    @Mock
    private RepositoryUtils repositoryUtils;

    @Mock
    private BookingUtils bookingUtils;

    @Test
    @DisplayName("Should check reservation availability")
    void shouldCheckAvailability() {

        var checkInDate = LocalDate.parse("2022-05-01");
        var checkOutDate = LocalDate.parse("2022-05-05");

        List<AvailabilityDto> responseMock = getAvailabilityDto(checkInDate, checkOutDate);

        when(bookingUtils.getAvailableDates(anyList(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(responseMock);

        var response = service.getAvailability(checkInDate, checkOutDate, null);

        assertEquals(responseMock, response);
    }

    @Test
    @DisplayName("Should create a reservation")
    void shouldCreateReservation() {

        var requestMock = getReservationRequestDto();
        var responseMock = getReservationResponseDto();

        var roomMock = RoomHelper.getRoom();
        when(repositoryUtils.getRoom(any(Long.class)))
                .thenReturn(roomMock);

        var customerMock = CustomerHelper.getCustomer();
        when(repositoryUtils.getCustomer(any(Long.class)))
                .thenReturn(customerMock);

        var roomsMock = List.of(roomMock);
        when(bookingUtils.getRooms(anyLong()))
                .thenReturn(roomsMock);

        var availabilityDtoMock = AvailabilityDtoHelper.getAvailabilityDto(requestMock.getCheckInDate(),
                requestMock.getCheckOutDate());
        when(bookingUtils.getAvailableDates(anyList(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(availabilityDtoMock);

        var response = service.create(requestMock);
        response.setReservationId(1L);

        assertEquals(responseMock, response);
    }

    @Test
    @DisplayName("Should modify a reservation")
    void shouldModifyReservation() {

        var requestMock = getAlterationRequestDto();
        var responseMock = getModifiedReservationResponseDto();

        var reservationMock = getReservation();
        when(repositoryUtils.getReservation(any(Long.class)))
                .thenReturn(reservationMock);

        var roomMock = RoomHelper.getRoom();
        when(repositoryUtils.getRoom(any(Long.class)))
                .thenReturn(roomMock);

        var customerMock = CustomerHelper.getCustomer();
        when(repositoryUtils.getCustomer(any(Long.class)))
                .thenReturn(customerMock);

        var roomsMock = List.of(roomMock);
        when(bookingUtils.getRooms(anyLong()))
                .thenReturn(roomsMock);

        var availabilityDtoMock = AvailabilityDtoHelper.getAvailabilityDto(requestMock.getCheckInDate(),
                requestMock.getCheckOutDate());
        when(bookingUtils.getAvailableDates(anyList(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(availabilityDtoMock);

        var response = service.modify(requestMock);

        assertEquals("altered", response.getStatus());
    }

    @Test
    @DisplayName("Should cancel a reservation")
    void shouldCancelReservation() {

        var requestMock = CancellationRequestDto.builder()
                .reservationId(1L)
                .build();

        var responseMock = getCanceledReservationResponseDto();

        var reservationMock = getReservation();
        when(repositoryUtils.getReservation(any(Long.class)))
                .thenReturn(reservationMock);

        var customerMock = CustomerHelper.getCustomer();
        when(repositoryUtils.getCustomer(any(Long.class)))
                .thenReturn(customerMock);

        var response = service.cancel(requestMock);

        assertEquals("canceled", response.getStatus());
    }

}