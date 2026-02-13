package com.hotelbooking.mobileapp.hotel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TwoStarHotelServiceImpl implements TwoStarHotelService {

    private final TwoStarHotelRepo repo;

    public TwoStarHotelServiceImpl(TwoStarHotelRepo repo) {
        this.repo = repo;
    }

    @Override
    public TwoStarHotel saveHotel(TwoStarHotel hotel) {
        return repo.save(hotel);
    }

    @Override
    public TwoStarHotel updateHotel(String registrationId, TwoStarHotel hotel) {
        TwoStarHotel existing = getByRegistrationId(registrationId);

        hotel.setRegistrationId(existing.getRegistrationId());
        hotel.setCreatedAt(existing.getCreatedAt());

        return repo.save(hotel);
    }

    @Override
    public TwoStarHotel getByRegistrationId(String registrationId) {
        return repo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @Override
    public TwoStarHotel getByHotelId(String hotelId) {
        return repo.findByHotelId(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @Override
    public List<TwoStarHotel> getByVendorId(String vendorId) {
        return repo.findByVendorId(vendorId);
    }

    @Override
    public List<TwoStarHotel> getAllHotels() {
        return repo.findAll();
    }

    @Override
    public void deleteHotel(String registrationId) {
        repo.deleteById(registrationId);
    }
}