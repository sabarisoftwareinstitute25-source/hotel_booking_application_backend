package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SixStarHotelRepository extends JpaRepository<SixStarHotel, String> {

    List<SixStarHotel> findByVendorId(String vendorId);

    List<SixStarHotel> findByRegistrationStatus(String status);

    List<SixStarHotel> findByCity(String city);
}