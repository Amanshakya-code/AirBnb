package com.aman.AirBnb.AirBnb.Entities;

import com.aman.AirBnb.AirBnb.Enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Booking")
public class BookingEntity {

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
    private Integer roomsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "booking_guest",joinColumns = @JoinColumn(name ="booking_id"),
    inverseJoinColumns = @JoinColumn(name = "guest_id"))
    private Set<GuestEntity> guests;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(unique = true)
    private String paymentSessionId;

}
