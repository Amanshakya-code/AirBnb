package com.aman.AirBnb.AirBnb.Service;

import com.aman.AirBnb.AirBnb.Dto.HotelDTO;
import com.aman.AirBnb.AirBnb.Entities.HotelEntity;
import com.aman.AirBnb.AirBnb.Entities.RoomEntity;
import com.aman.AirBnb.AirBnb.Exceptions.ResourceNotFoundException;
import com.aman.AirBnb.AirBnb.Repositories.HotelRepository;
import com.aman.AirBnb.AirBnb.Service.Interfaces.HotelService;
import com.aman.AirBnb.AirBnb.Service.Interfaces.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public HotelDTO createNewHotel(HotelDTO hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        HotelEntity hotel = modelMapper.map(hotelDto, HotelEntity.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}", hotel.getId());
        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}", id);
        HotelEntity hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));
        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    public HotelDTO updateHotelById(Long id, HotelDTO hotelDto) {
        log.info("Updating the hotel with ID: {}", id);
        HotelEntity hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        HotelEntity hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));

        hotelRepository.deleteById(id);
        for(RoomEntity room: hotel.getRooms()) {
            inventoryService.deleteFutureInventories(room);
        }
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        HotelEntity hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        hotel.setActive(true);

        // assuming only do it once
        for(RoomEntity room: hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }
    }
}
