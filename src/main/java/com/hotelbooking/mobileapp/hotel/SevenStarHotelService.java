package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SevenStarHotelService {

    private final SevenStarHotelRepository repository;

    public SevenStarHotel saveHotel(SevenStarHotel hotel) {
        return repository.save(hotel);
    }

    public List<SevenStarHotel> getAllHotels() {
        return repository.findAll();
    }

    public SevenStarHotel getHotelById(String hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }
}
