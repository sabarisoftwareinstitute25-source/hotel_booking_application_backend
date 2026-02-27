package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwoStarHotelServiceImpl implements TwoStarHotelService {

    private final TwoStarHotelRepository repository;

    @Override
    public TwoStarHotel registerHotel(TwoStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public TwoStarHotel getById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Two Star Hotel not found"));
    }

    @Override
    public List<TwoStarHotel> getAllHotels() {
        return repository.findAll();
    }

    @Override
    public List<TwoStarHotel> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }

    @Override
    public TwoStarHotel updateHotel(String registrationId, TwoStarHotel hotel) {

        TwoStarHotel existing = getById(registrationId);

        hotel.setRegistrationId(existing.getRegistrationId());
        hotel.setCreatedAt(existing.getCreatedAt());

        return repository.save(hotel);
    }

    @Override
    public void deleteHotel(String registrationId) {
        repository.deleteById(registrationId);
    }
}