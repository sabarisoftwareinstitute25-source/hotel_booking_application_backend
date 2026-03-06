package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelVendorService {

    private final HotelVendorRepository repository;

    public HotelVendor saveHotel(HotelVendor hotel) {
        return repository.save(hotel);
    }

    public List<HotelVendor> getAllHotels() {
        return repository.findAll();
    }

    public HotelVendor getHotelById(String hotelId) {
        return repository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(String hotelId) {
        repository.deleteById(hotelId);
    }
}