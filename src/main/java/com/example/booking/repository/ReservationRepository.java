package com.example.booking.repository;

import com.example.booking.model.Reservation;
import com.example.booking.projections.StartAndEndDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT checkInDate, checkOutDate from Reservation where room_id = id")
    List<StartAndEndDates> findUnavailableDates(Long id);

}
