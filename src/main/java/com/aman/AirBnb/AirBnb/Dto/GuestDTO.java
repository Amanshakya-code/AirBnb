package com.aman.AirBnb.AirBnb.Dto;

import com.aman.AirBnb.AirBnb.Entities.UserEntity;
import com.aman.AirBnb.AirBnb.Enums.Gender;
import lombok.Data;

@Data
public class GuestDTO {
    private Long id;
    private UserEntity user;
    private String name;
    private Gender gender;
    private Integer age;
}
