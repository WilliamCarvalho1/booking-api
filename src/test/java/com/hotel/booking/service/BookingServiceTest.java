package com.hotel.booking.service;

import com.hotel.booking.dto.CancellationRequestDto;
import com.hotel.booking.helper.CustomerHelper;
import com.hotel.booking.helper.RoomHelper;
import com.hotel.booking.utils.RepositoryUtils;
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
import static com.hotel.booking.helper.ReservationHelper.getReservation;
import static com.hotel.booking.mapper.CheckAvailabilityMapper.availableDatesToDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for BookingService")
class BookingServiceTest {

    @InjectMocks
    private BookingService service;

    @Mock
    private RepositoryUtils repositoryUtils;

    @Test
    @DisplayName("Should check reservation availability")
    void shouldCheckAvailability() {

        var checkInDate = LocalDate.parse("2022-05-08");
        var checkOutDate = LocalDate.parse("2022-05-10");

        List<LocalDate> dates = new ArrayList<>(0);
        dates.add(checkInDate);
        dates.add(LocalDate.parse("2022-05-09"));
        dates.add(checkOutDate);

        var responseMock = availableDatesToDto(dates);

        var roomMock = RoomHelper.getRoom();
        when(repositoryUtils.getRoom(any(Long.class)))
                .thenReturn(roomMock);

        var response = service.getRoomAvailability(checkInDate, checkOutDate, roomMock.getId());

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

        var response = service.create(requestMock);

        // Because the test is mocked, it doesn't access the database, so it doesn't generate the reservationID
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

        var response = service.modify(requestMock);

        // Because the test is mocked, it doesn't access the database, so it doesn't generate the reservationID
        response.setReservationId(1L);

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