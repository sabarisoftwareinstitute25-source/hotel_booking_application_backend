package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelVendorServiceImp implements HotelVendorService {

    private final HotelVendorRepository repository;

    @Override
    public HotelVendor saveHotelVendor(HotelVendor request) {
        return repository.save(request);
    }

    @Override
    public HotelVendor registerHotel(HotelVendor hotelVendor) {
        hotelVendor.setRegistrationId(generateRegistrationId());
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
        return repository.findByVendor_VendorId(vendorId);
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

    // -------------------------
    // ID GENERATOR METHOD
    // -------------------------
    private String generateRegistrationId() {

        String prefix = "NSH";

        String yearMonth = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMM"));

        long count = repository.count() + 1;

        return prefix + yearMonth + String.format("%04d", count);
    }



}