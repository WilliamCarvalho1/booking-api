package com.example.booking.repository;

import com.example.booking.dto.ReservationRequestDto;
import com.example.booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    //TODO alterar
    @Modifying
    @Query("UPDATE Reservation r SET free = false WHERE r.id = ?1 ")
    void updateReservation(ReservationRequestDto reservation);

    @Modifying
    @Query("DELETE Reservation r WHERE r.id = ?1 ")
    void delete(long id);

    Reservation findById(Long id);

    @Query("SELECT startDate, endDate from Reservation where room_id = id")
    List<LocalDate> findUnavailableDates(Long id);
}
