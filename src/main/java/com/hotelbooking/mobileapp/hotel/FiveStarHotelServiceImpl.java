package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiveStarHotelServiceImpl implements FiveStarHotelService {

    private final FiveStarHotelRepository repository;

    @Override
    public FiveStarHotel registerHotel(FiveStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public FiveStarHotel getById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Five Star Hotel not found"));
    }

    @Override
    public List<FiveStarHotel> getAllHotels() {
        return repository.findAll();
    }

    @Override
    public List<FiveStarHotel> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }

    @Override
    public List<FiveStarHotel> getByStatus(String status) {
        return repository.findByRegistrationStatus(status);
    }

    @Override
    public FiveStarHotel updateHotel(String registrationId, FiveStarHotel hotel) {
        FiveStarHotel existing = getById(registrationId);
        hotel.setRegistrationId(existing.getRegistrationId());
        hotel.setCreatedAt(existing.getCreatedAt());
        return repository.save(hotel);
    }

    @Override
    public void deleteHotel(String registrationId) {
        repository.deleteById(registrationId);
    }
}