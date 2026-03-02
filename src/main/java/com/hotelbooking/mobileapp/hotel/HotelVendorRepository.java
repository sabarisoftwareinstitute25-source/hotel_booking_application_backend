package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HotelVendorRepository extends JpaRepository<HotelVendor, String> {

    List<HotelVendor> findByVendor_VendorId(String vendorId);

    List<HotelVendor> findByRegistrationStatus(String status);
}