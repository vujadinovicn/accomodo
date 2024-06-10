package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.Booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.listing.owner.id = :ownerId")
    List<Booking> findBookingsByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT b FROM Booking b WHERE b.traveler.id = :travelerId")
    List<Booking> findBookingsByTravelerId(@Param("travelerId") Long travelerId);
}


