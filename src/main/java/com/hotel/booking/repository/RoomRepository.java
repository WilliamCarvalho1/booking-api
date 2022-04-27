package com.hotel.booking.repository;

import com.hotel.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT room from Room room where room.id = ?1 and room.blocked = 'false'")
    Room findUnblockedRoom(Long id);

    @Query("SELECT room from Room room where room.blocked = 'false'")
    List<Room> findUnblockedRooms();

}
