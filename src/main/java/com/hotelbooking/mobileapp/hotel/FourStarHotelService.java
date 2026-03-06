package com.hotelbooking.mobileapp.hotel;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FourStarHotelService {

    private final FourStarHotelRepository repository;

    public FourStarHotel saveHotel(FourStarHotel hotel) {
        return repository.save(hotel);
    }

    public List<FourStarHotel> getAllHotels() {
        return repository.findAll();
    }

    public FourStarHotel getHotelById(String hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }
}