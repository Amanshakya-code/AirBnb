package com.aman.AirBnb.AirBnb.Service;

import com.aman.AirBnb.AirBnb.Dto.BookingDTO;
import com.aman.AirBnb.AirBnb.Dto.BookingRequest;
import com.aman.AirBnb.AirBnb.Dto.GuestDTO;
import com.aman.AirBnb.AirBnb.Entities.*;
import com.aman.AirBnb.AirBnb.Enums.BookingStatus;
import com.aman.AirBnb.AirBnb.Exceptions.ResourceNotFoundException;
import com.aman.AirBnb.AirBnb.Repositories.*;
import com.aman.AirBnb.AirBnb.Service.Interfaces.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final GuestRepository guestRepository;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;


    @Override
    @Transactional
    public BookingDTO initialiseBooking(BookingRequest bookingRequest) {
        log.info("Initialising booking for hotel : {}, room: {}, date {}-{}", bookingRequest.getHotelId(),
                bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

        HotelEntity hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(() ->
                new ResourceNotFoundException("Hotel not found with id: "+bookingRequest.getHotelId()));

        RoomEntity room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() ->
                new ResourceNotFoundException("Room not found with id: "+bookingRequest.getRoomId()));

        List<InventoryEntity> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),
                bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount());

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate())+1;

        if (inventoryList.size() != daysCount) {
            throw new IllegalStateException("Room is not available anymore");
        }

        // Reserve the room/ update the booked count of inventories

        for(InventoryEntity inventory: inventoryList) {
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        // Create the Booking
        // TODO: calculate dynamic amount

        BookingEntity booking = BookingEntity.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);
    }

    @Override
    @Transactional
    public BookingDTO addGuests(Long bookingId, List<GuestDTO> guestDtoList) {
        log.info("Adding guests for booking with id: {}", bookingId);

        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new ResourceNotFoundException("Booking not found with id: "+bookingId));

        if (hasBookingExpired(booking)) {
            throw new IllegalStateException("Booking has already expired");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED) {
            throw new IllegalStateException("Booking is not under reserved state, cannot add guests");
        }

        for (GuestDTO guestDto: guestDtoList) {
            GuestEntity guest = modelMapper.map(guestDto, GuestEntity.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }

        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);
    }

    public boolean hasBookingExpired(BookingEntity booking) {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public UserEntity getCurrentUser() {
        UserEntity user = new UserEntity();
        user.setId(1L); // TODO: REMOVE DUMMY USER
        return user;
    }
}
