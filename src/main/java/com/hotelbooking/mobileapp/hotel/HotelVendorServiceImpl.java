package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelVendorServiceImpl implements HotelVendorService {

    private final HotelVendorRepository repository;

    @Override
    public HotelVendor registerHotel(HotelVendor hotelVendor) {
        return repository.save(hotelVendor);
    }

    @Override
    public HotelVendor getByRegistrationId(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @Override
    public List<HotelVendor> getAllHotels() {
        return repository.findAll();
    }

    @Override
    public List<HotelVendor> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }

    @Override
    public HotelVendor updateHotel(String registrationId, HotelVendor hotelVendor) {
        HotelVendor existing = getByRegistrationId(registrationId);
        hotelVendor.setRegistrationId(existing.getRegistrationId());
        return repository.save(hotelVendor);
    }

    @Override
    public void deleteHotel(String registrationId) {
        repository.deleteById(registrationId);
    }
}