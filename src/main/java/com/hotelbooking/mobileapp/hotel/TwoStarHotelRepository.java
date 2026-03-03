package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TwoStarHotelRepository extends JpaRepository<TwoStarHotel, String> {

    @Query("SELECT t.registrationId FROM TwoStarHotel t")
    List<String> findAllRegistrationIds();
}