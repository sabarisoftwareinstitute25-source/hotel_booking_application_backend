package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FourStarHotelServiceImpl implements FourStarHotelService {

    private final FourStarHotelRepository repository;

    @Override
    public FourStarHotel registerHotel(FourStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public FourStarHotel getById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Four Star Hotel not found"));
    }

    @Override
    public List<FourStarHotel> getAllHotels() {
        return repository.findAll();
    }

    @Override
    public List<FourStarHotel> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }

    @Override
    public List<FourStarHotel> getByStatus(String status) {
        return repository.findByRegistrationStatus(status);
    }

    @Override
    public FourStarHotel updateHotel(String registrationId, FourStarHotel hotel) {
        FourStarHotel existing = getById(registrationId);
        hotel.setRegistrationId(existing.getRegistrationId());
        hotel.setCreatedAt(existing.getCreatedAt());
        return repository.save(hotel);
    }

    @Override
    public void deleteHotel(String registrationId) {
        repository.deleteById(registrationId);
    }
}