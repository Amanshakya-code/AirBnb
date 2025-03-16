package com.aman.AirBnb.AirBnb.Service.Interfaces;



import com.aman.AirBnb.AirBnb.Dto.GuestDTO;

import java.util.List;

public interface GuestService {

    List<GuestDTO> getAllGuests();

    void updateGuest(Long guestId, GuestDTO guestDto);

    void deleteGuest(Long guestId);

    GuestDTO addNewGuest(GuestDTO guestDto);
}
