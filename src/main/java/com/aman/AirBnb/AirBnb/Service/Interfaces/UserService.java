package com.aman.AirBnb.AirBnb.Service.Interfaces;

import com.aman.AirBnb.AirBnb.Dto.ProfileUpdateRequestDTO;
import com.aman.AirBnb.AirBnb.Dto.UserDTO;
import com.aman.AirBnb.AirBnb.Entities.UserEntity;

public interface UserService {
    UserEntity getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDTO profileUpdateRequestDto);

    UserDTO getMyProfile();
}
