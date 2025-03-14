package com.aman.AirBnb.AirBnb.Repositories;

import com.aman.AirBnb.AirBnb.Entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity,Long> {
}
