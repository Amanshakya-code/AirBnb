package com.aman.AirBnb.AirBnb.Dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RoomDTO {
    private Long id;
    private String type;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount;
    private Integer capacity;
}
