package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreeStarHotelRepository extends JpaRepository<ThreeStarHotel, String> {

    @Query("SELECT t.hotelId FROM ThreeStarHotel t")
    List<String> findAllHotelIds();
}