package com.hotelbooking.mobileapp.hotel;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FourStarHotelServiceImpl implements FourStarHotelService {

    private final FourStarHotelRepository repository;

    public FourStarHotelServiceImpl(FourStarHotelRepository repository) {
        this.repository = repository;
    }

    @Override
    public FourStarHotel save(FourStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public FourStarHotel update(String registrationId, FourStarHotel hotel) {

        FourStarHotel existing = repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Four Star Hotel not found"));

        // Update required fields
        existing.setHotelName(hotel.getHotelName());
        existing.setOwnerName(hotel.getOwnerName());
        existing.setMobileNumber(hotel.getMobileNumber());
        existing.setEmail(hotel.getEmail());
        existing.setAddressLine1(hotel.getAddressLine1());
        existing.setCity(hotel.getCity());
        existing.setState(hotel.getState());
        existing.setPinCode(hotel.getPinCode());
        existing.setStandardCheckInTime(hotel.getStandardCheckInTime());
        existing.setStandardCheckOutTime(hotel.getStandardCheckOutTime());
        existing.setRegistrationStatus(hotel.getRegistrationStatus());

        return repository.save(existing);
    }

    @Override
    public FourStarHotel getById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @Override
    public List<FourStarHotel> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String registrationId) {
        repository.deleteById(registrationId);
    }

    @Override
    public List<FourStarHotel> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }
}