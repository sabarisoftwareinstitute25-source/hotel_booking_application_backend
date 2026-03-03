package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SixStarHotelRepository extends JpaRepository<SixStarHotel, String> {

    @Query("SELECT f.registrationId FROM SixStarHotel f")
    List<String> findAllRegistrationIds();
}