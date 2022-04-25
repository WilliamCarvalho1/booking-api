package com.hotel.booking.service;

import com.hotel.booking.helper.CustomerHelper;
import com.hotel.booking.helper.RoomHelper;
import com.hotel.booking.utils.RepositoryUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.hotel.booking.helper.ReservationDtoHelper.getReservationRequestDto;
import static com.hotel.booking.helper.ReservationDtoHelper.getReservationResponseDto;
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

}