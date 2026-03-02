package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiveStarHotelRepository extends JpaRepository<FiveStarHotel, String> {

    List<FiveStarHotel> findByVendorId(String vendorId);

    List<FiveStarHotel> findByRegistrationStatus(String registrationStatus);
}