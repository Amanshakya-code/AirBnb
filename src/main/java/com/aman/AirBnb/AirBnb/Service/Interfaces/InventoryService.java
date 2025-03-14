package com.aman.AirBnb.AirBnb.Service.Interfaces;

import com.aman.AirBnb.AirBnb.Entities.RoomEntity;

public interface InventoryService {
    void initializeRoomForAYear(RoomEntity room);

    void deleteFutureInventories(RoomEntity room);
}
