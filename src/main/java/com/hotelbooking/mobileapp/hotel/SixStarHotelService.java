package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SixStarHotelService {

    private final SixStarHotelRepository repository;

    public SixStarHotel saveHotel(SixStarHotel hotel) {
        return repository.save(hotel);
    }

    public List<SixStarHotel> getAllHotels() {
        return repository.findAll();
    }

    public SixStarHotel getHotelById(String hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }
}