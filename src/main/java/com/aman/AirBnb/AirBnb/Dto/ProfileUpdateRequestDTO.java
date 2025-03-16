package com.aman.AirBnb.AirBnb.Dto;

import com.aman.AirBnb.AirBnb.Enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDTO {
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
}
