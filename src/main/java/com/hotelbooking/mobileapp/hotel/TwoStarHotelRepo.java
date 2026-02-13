package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TwoStarHotelRepo extends JpaRepository<TwoStarHotel, String> {

    Optional<TwoStarHotel> findByHotelId(String hotelId);

    List<TwoStarHotel> findByVendorId(String vendorId);

    List<TwoStarHotel> findByRegistrationStatus(String registrationStatus);
}