package com.aman.AirBnb.AirBnb.Service.Interfaces;

import com.aman.AirBnb.AirBnb.Dto.HotelDTO;
import com.aman.AirBnb.AirBnb.Dto.HotelSearchRequest;
import com.aman.AirBnb.AirBnb.Entities.RoomEntity;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForAYear(RoomEntity room);

    void deleteAllInventories(RoomEntity room);

    Page<HotelDTO> searchHotels(HotelSearchRequest hotelSearchRequest);
}
