package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlobalEliteHotelRepository extends JpaRepository<GlobalEliteHotel, String> {

    Optional<GlobalEliteHotel> findByVendorId(String vendorId);
}