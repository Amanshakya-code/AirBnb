package com.aman.AirBnb.AirBnb.Service;

import com.aman.AirBnb.AirBnb.Dto.RoomDTO;
import com.aman.AirBnb.AirBnb.Entities.HotelEntity;
import com.aman.AirBnb.AirBnb.Entities.RoomEntity;
import com.aman.AirBnb.AirBnb.Exceptions.ResourceNotFoundException;
import com.aman.AirBnb.AirBnb.Repositories.HotelRepository;
import com.aman.AirBnb.AirBnb.Repositories.RoomRepository;
import com.aman.AirBnb.AirBnb.Service.Interfaces.InventoryService;
import com.aman.AirBnb.AirBnb.Service.Interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDTO createNewRoom(Long hotelId, RoomDTO roomDto) {
        log.info("Creating a new room in hotel with ID: {}", hotelId);
        HotelEntity hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));
        RoomEntity room = modelMapper.map(roomDto, RoomEntity.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        if (hotel.getActive()) {
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room, RoomDTO.class);
    }

    @Override
    public List<RoomDTO> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all rooms in hotel with ID: {}", hotelId);
        HotelEntity hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(Long roomId) {
        log.info("Getting the room with ID: {}", roomId);
        RoomEntity room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));
        return modelMapper.map(room, RoomDTO.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the room with ID: {}", roomId);
        RoomEntity room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));
        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }
}
