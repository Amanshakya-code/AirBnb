package com.aman.AirBnb.AirBnb.Repositories;

import com.aman.AirBnb.AirBnb.Entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
}
