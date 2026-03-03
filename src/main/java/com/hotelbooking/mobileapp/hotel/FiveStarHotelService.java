package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FiveStarHotelService {

    private final FiveStarHotelRepository repository;

    public FiveStarHotel saveHotel(FiveStarHotel hotel) {
        return repository.save(hotel);
    }

    public List<FiveStarHotel> getAllHotels() {
        return repository.findAll();
    }

    public FiveStarHotel getHotelById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String registrationId) {
        repository.deleteById(registrationId);
    }
}