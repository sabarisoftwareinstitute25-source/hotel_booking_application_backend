package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SevenStarHotelRepository extends JpaRepository<SevenStarHotel, String> {

    Optional<SevenStarHotel> findByVendorId(String vendorId);
}