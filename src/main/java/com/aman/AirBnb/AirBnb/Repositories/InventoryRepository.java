package com.aman.AirBnb.AirBnb.Repositories;

import com.aman.AirBnb.AirBnb.Dto.RoomDTO;
import com.aman.AirBnb.AirBnb.Entities.HotelEntity;
import com.aman.AirBnb.AirBnb.Entities.InventoryEntity;
import com.aman.AirBnb.AirBnb.Entities.RoomEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity,Long> {

    void deleteByRoom(RoomEntity room);

    @Query("""
           SELECT DISTINCT i.hotel
           FROM InventoryEntity i
           WHERE i.city = :city
                AND i.date BETWEEN :startDate AND :endDate
                AND i.closed = false
                AND (i.totalCount - i.bookCount - i.reservedCount) >= :roomsCount
           GROUP BY i.hotel, i.room
           HAVING COUNT(i.date) = :dateCount
           """)
    Page<HotelEntity> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );

    @Query("""
            SELECT i
            FROM InventoryEntity i
            WHERE i.room.id = :roomId
                AND i.date BETWEEN :startDate AND :endDate
                AND i.closed = false
                AND (i.totalCount - i.bookCount - i.reservedCount) >= :roomsCount
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<InventoryEntity> findAndLockAvailableInventory(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount
    );

    List<InventoryEntity> findByHotelAndDateBetween(HotelEntity hotel, LocalDate startDate, LocalDate endDate);

    @Query("""
                SELECT i
                FROM InventoryEntity i
                WHERE i.room.id = :roomId
                  AND i.date BETWEEN :startDate AND :endDate
                  AND (i.totalCount - i.bookCount) >= :numberOfRooms
                  AND i.closed = false
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<InventoryEntity> findAndLockReservedInventory(@Param("roomId") Long roomId,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate,
                                                 @Param("numberOfRooms") int numberOfRooms);

    @Modifying
    @Query("""
                UPDATE InventoryEntity i
                SET i.reservedCount = i.reservedCount - :numberOfRooms,
                    i.bookCount = i.bookCount + :numberOfRooms
                WHERE i.room.id = :roomId
                  AND i.date BETWEEN :startDate AND :endDate
                  AND (i.totalCount - i.bookCount) >= :numberOfRooms
                  AND i.reservedCount >= :numberOfRooms
                  AND i.closed = false
            """)
    void confirmBooking(@Param("roomId") Long roomId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("numberOfRooms") int numberOfRooms);

    @Modifying
    @Query("""
                UPDATE InventoryEntity i
                SET i.bookCount = i.bookCount - :numberOfRooms
                WHERE i.room.id = :roomId
                  AND i.date BETWEEN :startDate AND :endDate
                  AND (i.totalCount - i.bookCount) >= :numberOfRooms
                  AND i.closed = false
            """)
    void cancelBooking(@Param("roomId") Long roomId,
                       @Param("startDate") LocalDate startDate,
                       @Param("endDate") LocalDate endDate,
                       @Param("numberOfRooms") int numberOfRooms);

    @Modifying
    @Query("""
                UPDATE InventoryEntity i
                SET i.reservedCount = i.reservedCount + :numberOfRooms
                WHERE i.room.id = :roomId
                  AND i.date BETWEEN :startDate AND :endDate
                  AND (i.totalCount - i.bookCount - i.reservedCount) >= :numberOfRooms
                  AND i.closed = false
            """)
    void initBooking(@Param("roomId") Long roomId,
                     @Param("startDate") LocalDate startDate,
                     @Param("endDate") LocalDate endDate,
                     @Param("numberOfRooms") int numberOfRooms);
}
