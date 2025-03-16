package com.aman.AirBnb.AirBnb.Repositories;

import com.aman.AirBnb.AirBnb.Dto.GuestDTO;
import com.aman.AirBnb.AirBnb.Entities.GuestEntity;
import com.aman.AirBnb.AirBnb.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
    List<GuestDTO> findByUser(UserEntity user);
}