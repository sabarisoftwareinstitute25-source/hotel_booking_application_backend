package com.hotelbooking.mobileapp.hotel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class TwoStarHotelServiceImp implements TwoStarHotelService {

    private final TwoStarHotelRepository twoStarHotelRepository;
    private final VendorRepository vendorRepository;

    public TwoStarHotelServiceImp(TwoStarHotelRepository hotelRepository, VendorRepository vendorRepository) {
        this.twoStarHotelRepository = hotelRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public TwoStarHotel saveHotelVendor(TwoStarHotel request) {
        if (request.getRegistrationId() == null || request.getRegistrationId().isEmpty()) {
            request.setRegistrationId(generateRegistrationId());
        }

        // 2️⃣ Save the hotel entity
        return twoStarHotelRepository.save(request);
    }

    @Override
    public TwoStarHotel registerHotel(TwoStarHotel twoStarHotel) {
        // Same as save
        return saveHotelVendor(twoStarHotel);
    }

    @Override
    public TwoStarHotel getByRegistrationId(String registrationId) {
        return twoStarHotelRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @Override
    public List<TwoStarHotel> getAllHotels() {
        return twoStarHotelRepository.findAll();
    }

    @Override
    public List<TwoStarHotel> getByVendor(String vendorId) {
        return twoStarHotelRepository.findByVendor_VendorId(vendorId);
    }

    @Override
    public TwoStarHotel updateHotel(String registrationId, TwoStarHotel twoStarHotel) {
        TwoStarHotel existing = getByRegistrationId(registrationId);
        // Update only the fields you have (here we have registrationId & vendor)
        if (twoStarHotel.getVendor() != null) {
            existing.setVendor(twoStarHotel.getVendor());
        }
        return twoStarHotelRepository.save(existing);
    }

    @Override
    public void deleteHotel(String registrationId) {
        TwoStarHotel existing = getByRegistrationId(registrationId);
        twoStarHotelRepository.delete(existing);
    }

    // -------------------------
    // ID GENERATOR METHOD
    // -------------------------
    private String generateRegistrationId() {

        String prefix = "2SH";

        String yearMonth = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMM"));

        long count = twoStarHotelRepository.count() + 1;

        return prefix + yearMonth + String.format("%04d", count);
    }

}