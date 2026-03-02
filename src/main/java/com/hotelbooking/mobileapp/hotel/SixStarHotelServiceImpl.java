package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SixStarHotelServiceImpl implements SixStarHotelService {

    private final SixStarHotelRepository repository;

    @Override
    public SixStarHotel registerHotel(SixStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public SixStarHotel getById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Six Star Hotel not found"));
    }

    @Override
    public List<SixStarHotel> getAllHotels() {
        return repository.findAll();
    }

    @Override
    public List<SixStarHotel> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }

    @Override
    public List<SixStarHotel> getByStatus(String status) {
        return repository.findByRegistrationStatus(status);
    }

    @Override
    public SixStarHotel updateHotel(String registrationId, SixStarHotel hotel) {
        SixStarHotel existing = getById(registrationId);
        hotel.setRegistrationId(existing.getRegistrationId());
        hotel.setCreatedAt(existing.getCreatedAt());
        return repository.save(hotel);
    }

    @Override
    public void deleteHotel(String registrationId) {
        repository.deleteById(registrationId);
    }
}