package com.aman.AirBnb.AirBnb.Repositories;

import com.aman.AirBnb.AirBnb.Entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}
