package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TwoStarHotelRepository extends JpaRepository<TwoStarHotel, String> {

    List<TwoStarHotel> findByVendor_VendorId(String vendorId);

    List<TwoStarHotel> findByRegistrationStatus(String registrationStatus);

}