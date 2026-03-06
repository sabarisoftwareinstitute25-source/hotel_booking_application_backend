package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GlobalEliteHotelRepository extends JpaRepository<GlobalEliteHotel, String> {

    @Query("SELECT f.hotelId FROM GlobalEliteHotel f")
    List<String> findAllHotelIds();
}