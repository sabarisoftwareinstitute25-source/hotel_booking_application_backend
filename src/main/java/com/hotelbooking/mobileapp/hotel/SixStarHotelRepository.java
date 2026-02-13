package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SixStarHotelRepository extends JpaRepository<SixStarHotel, String> {

    List<SixStarHotel> findByVendorId(String vendorId);

    boolean existsByHotelId(String hotelId);
}