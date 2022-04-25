package com.hotel.booking.repository;

import com.hotel.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT room from Room room where room.id = ?1 and room.blocked = 'false'")
    Room findUnblockedRoom(Long id);

}
