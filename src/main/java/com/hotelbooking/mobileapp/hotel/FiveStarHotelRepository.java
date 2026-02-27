package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FiveStarHotelRepository extends JpaRepository<FiveStarHotel, String> {

    List<FiveStarHotel> findByVendorId(String vendorId);

    List<FiveStarHotel> findByRegistrationStatus(String status);

    List<FiveStarHotel> findByCity(String city);
}