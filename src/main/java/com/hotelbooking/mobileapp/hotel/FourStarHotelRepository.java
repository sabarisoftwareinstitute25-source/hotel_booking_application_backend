package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FourStarHotelRepository extends JpaRepository<FourStarHotel, String> {

    List<FourStarHotel> findByVendorId(String vendorId);

    List<FourStarHotel> findByRegistrationStatus(String status);

    List<FourStarHotel> findByCity(String city);
}