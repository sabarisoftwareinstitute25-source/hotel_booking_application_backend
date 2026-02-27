package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SevenStarHotelServiceImpl implements SevenStarHotelService {

    private final SevenStarHotelRepository repository;

    @Override
    public SevenStarHotel createHotel(SevenStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public SevenStarHotel getByRegistrationId(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + registrationId));
    }

    @Override
    public List<SevenStarHotel> getAllHotels() {
        return repository.findAll();
    }

    @Override
    public SevenStarHotel updateHotel(String registrationId, SevenStarHotel updatedHotel) {

        SevenStarHotel existing = getByRegistrationId(registrationId);

        updatedHotel.setRegistrationId(existing.getRegistrationId());
        updatedHotel.setCreatedAt(existing.getCreatedAt());

        return repository.save(updatedHotel);
    }

    @Override
    public void deleteHotel(String registrationId) {
        SevenStarHotel existing = getByRegistrationId(registrationId);
        repository.delete(existing);
    }
}