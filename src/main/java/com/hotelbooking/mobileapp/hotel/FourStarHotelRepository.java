package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FourStarHotelRepository
        extends JpaRepository<FourStarHotel, String> {

    @Query("SELECT f.registrationId FROM FourStarHotel f")
    List<String> findAllRegistrationIds();
}