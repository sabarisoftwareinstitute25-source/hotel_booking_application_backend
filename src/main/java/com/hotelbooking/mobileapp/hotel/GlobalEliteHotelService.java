package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalEliteHotelService {

    private final GlobalEliteHotelRepository repository;

    public GlobalEliteHotel saveHotel(GlobalEliteHotel hotel) {
        return repository.save(hotel);
    }

    public List<GlobalEliteHotel> getAllHotels() {
        return repository.findAll();
    }

    public GlobalEliteHotel getHotelById(String hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }
}