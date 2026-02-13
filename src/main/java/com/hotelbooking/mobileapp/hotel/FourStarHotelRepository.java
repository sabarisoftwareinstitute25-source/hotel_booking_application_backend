package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FourStarHotelRepository extends JpaRepository<FourStarHotel, String> {

    List<FourStarHotel> findByVendorId(String vendorId);

}