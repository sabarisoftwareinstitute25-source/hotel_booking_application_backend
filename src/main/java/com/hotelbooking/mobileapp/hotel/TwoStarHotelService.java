package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TwoStarHotelService {

    private final TwoStarHotelRepository repository;

    public TwoStarHotelService(TwoStarHotelRepository repository) {
        this.repository = repository;
    }

    // Save Hotel
    public TwoStarHotel saveHotel(TwoStarHotel hotel) {
        return repository.save(hotel);
    }

    // Get All Hotels
    public List<TwoStarHotel> getAllHotels() {
        return repository.findAll();
    }

    // Get By ID
    public Optional<TwoStarHotel> getHotelById(String hotelId) {
        return repository.findById(hotelId);
    }

    // Delete
    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }
}