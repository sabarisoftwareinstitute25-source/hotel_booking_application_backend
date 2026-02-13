package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for HotelVendor entity.
 */
@Repository
public interface HotelVendorRepository extends JpaRepository<HotelVendor, String> {

    /**
     * Find vendor by hotel ID.
     */
    Optional<HotelVendor> findByHotelId(String hotelId);

    /**
     * Find vendors by registration status.
     */
    List<HotelVendor> findByRegistrationStatus(String status);

    /**
     * Find vendors by city.
     */
    List<HotelVendor> findByCity(String city);

    /**
     * Find vendors by owner email.
     */
    Optional<HotelVendor> findByEmail(String email);

    /**
     * Find vendors by owner mobile number.
     */
    Optional<HotelVendor> findByMobileNumber(String mobileNumber);

    /**
     * Check if vendor exists by hotel ID.
     */
    boolean existsByHotelId(String hotelId);
}

