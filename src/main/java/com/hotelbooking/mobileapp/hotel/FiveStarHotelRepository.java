package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiveStarHotelRepository extends JpaRepository<FiveStarHotel, String> {
    @Query("SELECT f.registrationId FROM FiveStarHotel f")
    List<String> findAllRegistrationIds();
}