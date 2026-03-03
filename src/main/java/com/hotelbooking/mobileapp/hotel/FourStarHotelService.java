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

    public FourStarHotel getHotelById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String registrationId) {
        repository.deleteById(registrationId);
    }
}