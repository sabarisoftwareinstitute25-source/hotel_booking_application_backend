package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TwoStarHotelRepository extends JpaRepository<TwoStarHotel, String> {

    List<TwoStarHotel> findByVendorId(String vendorId);

    List<TwoStarHotel> findByRegistrationStatus(String registrationStatus);

    List<TwoStarHotel> findByCity(String city);
}