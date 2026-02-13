package com.hotelbooking.mobileapp.hotel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SixStarHotelServiceImpl implements SixStarHotelService {

    private final SixStarHotelRepository repository;

    public SixStarHotelServiceImpl(SixStarHotelRepository repository) {
        this.repository = repository;
    }

    @Override
    public SixStarHotel save(SixStarHotel hotel) {

        if (repository.existsByHotelId(hotel.getHotelId())) {
            throw new RuntimeException("Hotel ID already exists");
        }

        return repository.save(hotel);
    }

    @Override
    public SixStarHotel update(String registrationId, SixStarHotel hotel) {

        SixStarHotel existing = repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.setRegistrationId(existing.getRegistrationId());
        hotel.setCreatedAt(existing.getCreatedAt());

        return repository.save(hotel);
    }

    @Override
    public SixStarHotel getById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @Override
    public List<SixStarHotel> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String registrationId) {

        SixStarHotel hotel = repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        repository.delete(hotel);
    }

    @Override
    public List<SixStarHotel> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }
}