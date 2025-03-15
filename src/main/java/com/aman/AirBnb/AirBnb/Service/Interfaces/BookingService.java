package com.aman.AirBnb.AirBnb.Service.Interfaces;

import com.aman.AirBnb.AirBnb.Dto.BookingDTO;
import com.aman.AirBnb.AirBnb.Dto.BookingRequest;
import com.aman.AirBnb.AirBnb.Dto.GuestDTO;

import java.util.List;

public interface BookingService {
    BookingDTO initialiseBooking(BookingRequest bookingRequest);

    BookingDTO addGuests(Long bookingId, List<GuestDTO> guestDtoList);
}
