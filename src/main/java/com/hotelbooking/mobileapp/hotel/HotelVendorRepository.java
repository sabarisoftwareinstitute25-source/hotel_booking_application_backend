package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelVendorRepository extends JpaRepository<HotelVendor, String> {

    @Query("SELECT f.hotelId FROM HotelVendor f")
    List<String> findAllHotelIds();
}