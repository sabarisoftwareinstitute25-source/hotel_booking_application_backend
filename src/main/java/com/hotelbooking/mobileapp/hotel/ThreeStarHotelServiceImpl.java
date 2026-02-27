package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreeStarHotelServiceImpl implements ThreeStarHotelService {

    private final ThreeStarHotelRepository repository;

    @Override
    public ThreeStarHotel registerHotel(ThreeStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public ThreeStarHotel getById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Three Star Hotel not found"));
    }

    @Override
    public List<ThreeStarHotel> getAllHotels() {
        return repository.findAll();
    }

    @Override
    public List<ThreeStarHotel> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }

    @Override
    public List<ThreeStarHotel> getByStatus(String status) {
        return repository.findByRegistrationStatus(status);
    }

    @Override
    public ThreeStarHotel updateHotel(String registrationId, ThreeStarHotel hotel) {

        ThreeStarHotel existing = getById(registrationId);

        hotel.setRegistrationId(existing.getRegistrationId());
        hotel.setCreatedAt(existing.getCreatedAt());

        return repository.save(hotel);
    }

    @Override
    public void deleteHotel(String registrationId) {
        repository.deleteById(registrationId);
    }
}