package com.aman.AirBnb.AirBnb.Dto;

import com.aman.AirBnb.AirBnb.Entities.HotelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceDTO {
    private HotelEntity hotel;
    private Double price;
}
