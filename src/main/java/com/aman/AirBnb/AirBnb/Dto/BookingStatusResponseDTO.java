package com.aman.AirBnb.AirBnb.Dto;

import com.aman.AirBnb.AirBnb.Enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingStatusResponseDTO {
    private BookingStatus bookingStatus;
}
