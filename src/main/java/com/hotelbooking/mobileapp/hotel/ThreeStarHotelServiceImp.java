package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreeStarHotelServiceImp implements ThreeStarHotelService {

    private final ThreeStarHotelRepository threeStarHotelRepository;
    private final VendorRepository vendorRepository;

    @Override
    public ThreeStarHotel registerHotel(ThreeStarHotel hotel) {

        return threeStarHotelRepository.save(hotel);
    }

    @Override
    public ThreeStarHotel getById(String registrationId) {
        return threeStarHotelRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Three Star Hotel not found"));
    }

    @Override
    public List<ThreeStarHotel> getAllHotels() {
        return threeStarHotelRepository.findAll();
    }

    @Override
    public List<ThreeStarHotel> getByVendor(String vendorId) {
        return threeStarHotelRepository.findByVendor_VendorId(vendorId);
    }

    @Override
    public List<ThreeStarHotel> getByStatus(String status) {
        return threeStarHotelRepository.findByRegistrationStatus(status);
    }

    @Override
    public ThreeStarHotel updateHotel(String registrationId, ThreeStarHotel hotel) {

        ThreeStarHotel existing = getById(registrationId);

        hotel.setRegistrationId(existing.getRegistrationId());
        hotel.setCreatedAt(existing.getCreatedAt());

        return threeStarHotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(String registrationId) {
        threeStarHotelRepository.deleteById(registrationId);
    }


    // -------------------------
    // ID GENERATOR METHOD
    // -------------------------
    private String generateRegistrationId() {

        String prefix = "3SH";

        String yearMonth = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMM"));

        long count = threeStarHotelRepository.count() + 1;

        return prefix + yearMonth + String.format("%04d", count);
    }
}