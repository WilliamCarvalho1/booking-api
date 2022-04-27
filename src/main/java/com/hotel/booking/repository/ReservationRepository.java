package com.hotel.booking.repository;

import com.hotel.booking.model.Reservation;
import com.hotel.booking.projections.RoomsBookedDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT reservation from Reservation reservation where reservation.status in ('active', 'altered')")
    List<RoomsBookedDates> findUnavailableDates();

}
