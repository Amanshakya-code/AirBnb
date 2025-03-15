package com.aman.AirBnb.AirBnb.Controller;

import com.aman.AirBnb.AirBnb.Dto.HotelDTO;
import com.aman.AirBnb.AirBnb.Service.Interfaces.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDTO> createNewHotel(@RequestBody HotelDTO hotelDto) {
        log.info("Attempting to create a new hotel with name: "+hotelDto.getName());
        HotelDTO hotel = hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long hotelId) {
        HotelDTO hotelDto = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelDto);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> updateHotelById(@PathVariable Long hotelId, @RequestBody HotelDTO hotelDto) {
        HotelDTO hotel = hotelService.updateHotelById(hotelId, hotelDto);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId) {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hotelId}/activate")
    public ResponseEntity<Void> activateHotel(@PathVariable Long hotelId) {
        hotelService.activateHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

}
