package com.aman.AirBnb.AirBnb.Service;

import com.aman.AirBnb.AirBnb.Entities.InventoryEntity;
import com.aman.AirBnb.AirBnb.Entities.RoomEntity;
import com.aman.AirBnb.AirBnb.Repositories.InventoryRepository;
import com.aman.AirBnb.AirBnb.Service.Interfaces.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public void initializeRoomForAYear(RoomEntity room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        for (; !today.isAfter(endDate); today=today.plusDays(1)) {
            InventoryEntity inventory = InventoryEntity.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteFutureInventories(RoomEntity room) {
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByDateAfterAndRoom(today, room);
    }
}
