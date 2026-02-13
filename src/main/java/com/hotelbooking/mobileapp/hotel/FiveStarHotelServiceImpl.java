package com.hotelbooking.mobileapp.hotel;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FiveStarHotelServiceImpl implements FiveStarHotelService {

    private final FiveStarHotelRepository repository;

    public FiveStarHotelServiceImpl(FiveStarHotelRepository repository) {
        this.repository = repository;
    }

    @Override
    public FiveStarHotel save(FiveStarHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public FiveStarHotel update(String registrationId, FiveStarHotel hotel) {
        FiveStarHotel existing = getById(registrationId);

        hotel.setRegistrationId(existing.getRegistrationId());
        return repository.save(hotel);
    }

    @Override
    public FiveStarHotel getById(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("FiveStarHotel not found"));
    }

    @Override
    public List<FiveStarHotel> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String registrationId) {
        repository.deleteById(registrationId);
    }

    @Override
    public List<FiveStarHotel> getByVendor(String vendorId) {
        return repository.findByVendorId(vendorId);
    }
}
