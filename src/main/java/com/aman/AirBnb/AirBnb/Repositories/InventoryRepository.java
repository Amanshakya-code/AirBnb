package com.aman.AirBnb.AirBnb.Repositories;

import com.aman.AirBnb.AirBnb.Entities.InventoryEntity;
import com.aman.AirBnb.AirBnb.Entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity,Long> {
    void deleteByDateAfterAndRoom(LocalDate date, RoomEntity room);
}
