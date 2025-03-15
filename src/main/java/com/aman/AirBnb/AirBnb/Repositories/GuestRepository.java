package com.aman.AirBnb.AirBnb.Repositories;

import com.aman.AirBnb.AirBnb.Entities.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
}