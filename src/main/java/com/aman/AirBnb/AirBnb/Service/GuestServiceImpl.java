package com.aman.AirBnb.AirBnb.Service;

import com.aman.AirBnb.AirBnb.Dto.GuestDTO;
import com.aman.AirBnb.AirBnb.Entities.GuestEntity;
import com.aman.AirBnb.AirBnb.Entities.UserEntity;
import com.aman.AirBnb.AirBnb.Repositories.GuestRepository;
import com.aman.AirBnb.AirBnb.Service.Interfaces.GuestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.aman.AirBnb.AirBnb.Utils.AppUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GuestDTO> getAllGuests() {
        UserEntity user = getCurrentUser();
        log.info("Fetching all guests of user with id: {}", user.getId());
        List<GuestDTO> guests = guestRepository.findByUser(user);
        return guests.stream()
                .map(guest -> modelMapper.map(guest, GuestDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateGuest(Long guestId, GuestDTO guestDto) {
        log.info("Updating guest with ID: {}", guestId);
        GuestEntity guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found"));

        UserEntity user = getCurrentUser();
        if(!user.equals(guest.getUser())) throw new AccessDeniedException("You are not the owner of this guest");

        modelMapper.map(guestDto, guest);
        guest.setUser(user);
        guest.setId(guestId);

        guestRepository.save(guest);
        log.info("Guest with ID: {} updated successfully", guestId);
    }

    @Override
    public void deleteGuest(Long guestId) {
        log.info("Deleting guest with ID: {}", guestId);
        GuestEntity guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found"));

        UserEntity user = getCurrentUser();
        if(!user.equals(guest.getUser())) throw new AccessDeniedException("You are not the owner of this guest");

        guestRepository.deleteById(guestId);
        log.info("Guest with ID: {} deleted successfully", guestId);
    }

    @Override
    public GuestDTO addNewGuest(GuestDTO guestDto) {
        log.info("Adding new guest: {}", guestDto);
        UserEntity user = getCurrentUser();
        GuestEntity guest = modelMapper.map(guestDto, GuestEntity.class);
        guest.setUser(user);
        GuestEntity savedGuest = guestRepository.save(guest);
        log.info("Guest added with ID: {}", savedGuest.getId());
        return modelMapper.map(savedGuest, GuestDTO.class);
    }
}
