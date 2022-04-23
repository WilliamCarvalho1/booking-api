package com.example.booking.repository;

import com.example.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Modifying
    @Query("UPDATE Room r SET free = false WHERE r.id = ?1 ")
    void reserveRoom(long id);

    @Modifying
    @Query("UPDATE Room r SET free = true WHERE r.id = ?1 ")
    void freeRoom(long id);

    @Query("SELECT Room r WHERE r.flagAvailable = true")
    List<Room> findAllAvailableRooms();

    Optional<Room> findById(Long id);

}
