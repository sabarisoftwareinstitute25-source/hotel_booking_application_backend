package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SevenStarHotelRepository extends JpaRepository<SevenStarHotel, String> {

    @Query("SELECT f.registrationId FROM SevenStarHotel f")
    List<String> findAllRegistrationIds();
}