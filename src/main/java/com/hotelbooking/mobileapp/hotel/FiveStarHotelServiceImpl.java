package com.hotelbooking.mobileapp.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FiveStarHotelServiceImpl implements FiveStarHotelService {

    @Autowired
    private FiveStarHotelRepository repository;

    @Override
    public FiveStarHotel save(FiveStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public List<FiveStarHotel> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<FiveStarHotel> getById(String registrationId) {
        return repository.findById(registrationId);
    }

    @Override
    public List<FiveStarHotel> getByVendorId(String vendorId) {
        return repository.findByVendorId(vendorId);
    }

    @Override
    public List<FiveStarHotel> getByStatus(String status) {
        return repository.findByRegistrationStatus(status);
    }

    @Override
    public FiveStarHotel update(String registrationId, FiveStarHotel hotel) {
        hotel.setRegistrationId(registrationId);
        return repository.save(hotel);
    }

    @Override
    public void delete(String registrationId) {
        repository.deleteById(registrationId);
    }
}