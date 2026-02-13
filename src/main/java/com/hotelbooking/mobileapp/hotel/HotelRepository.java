package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
    
    @Query("SELECT h FROM Hotel h WHERE " +
           "(:city IS NULL OR h.city = :city) AND " +
           "(:country IS NULL OR h.country = :country) AND " +
           "(:starRating IS NULL OR h.starRating = :starRating)")
    List<Hotel> searchHotels(
        @Param("city") String city,
        @Param("country") String country,
        @Param("starRating") Integer starRating
    );
    
    List<Hotel> findByCity(String city);
    List<Hotel> findByCountry(String country);
    List<Hotel> findByStarRating(Integer starRating);
}

