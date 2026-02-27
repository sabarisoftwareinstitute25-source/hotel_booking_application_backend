package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ThreeStarHotelRepository extends JpaRepository<ThreeStarHotel, String> {

    List<ThreeStarHotel> findByVendorId(String vendorId);

    List<ThreeStarHotel> findByRegistrationStatus(String registrationStatus);

    List<ThreeStarHotel> findByCity(String city);

}