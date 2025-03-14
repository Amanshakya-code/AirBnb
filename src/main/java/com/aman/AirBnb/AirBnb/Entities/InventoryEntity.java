package com.aman.AirBnb.AirBnb.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Inventory",
        uniqueConstraints = @UniqueConstraint(name = "unique_hotel_room_date",columnNames = {"hotel_id","room_id","date"}))
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id",nullable = false)
    private HotelEntity hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private RoomEntity room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false,columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookCount;

    @Column(nullable = false,columnDefinition = "INTEGER DEFAULT 0")
    private Integer totalCount;

    @Column(nullable = false,precision = 5,scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Boolean closed;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
