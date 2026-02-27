package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalEliteHotelServiceImpl implements GlobalEliteHotelService {

    private final GlobalEliteHotelRepository repository;

    @Override
    public GlobalEliteHotel create(GlobalEliteHotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public GlobalEliteHotel getByRegistrationId(String registrationId) {
        return repository.findById(registrationId)
                .orElseThrow(() ->
                        new RuntimeException("Global Elite Hotel not found with ID: " + registrationId));
    }

    @Override
    public List<GlobalEliteHotel> getAll() {
        return repository.findAll();
    }

    @Override
    public GlobalEliteHotel update(String registrationId, GlobalEliteHotel updatedHotel) {

        GlobalEliteHotel existing = getByRegistrationId(registrationId);

        // Preserve immutable fields
        updatedHotel.setRegistrationId(existing.getRegistrationId());
        updatedHotel.setCreatedAt(existing.getCreatedAt());

        return repository.save(updatedHotel);
    }

    @Override
    public void delete(String registrationId) {
        GlobalEliteHotel existing = getByRegistrationId(registrationId);
        repository.delete(existing);
    }
}