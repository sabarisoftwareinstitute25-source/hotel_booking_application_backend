package com.hotelbooking.mobileapp.hotel;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThreeStarHotelService {

    private final ThreeStarHotelRepository repository;

    public ThreeStarHotelService(ThreeStarHotelRepository repository) {
        this.repository = repository;
    }

    // Save Hotel
    public ThreeStarHotel saveHotel(ThreeStarHotel hotel) {
        return repository.save(hotel);
    }

    // Get All Hotels
    public List<ThreeStarHotel> getAllHotels() {
        return repository.findAll();
    }

    // Get By ID
    public Optional<ThreeStarHotel> getHotelById(String hotelId) {
        return repository.findById(hotelId);
    }

    // Delete
    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }

}