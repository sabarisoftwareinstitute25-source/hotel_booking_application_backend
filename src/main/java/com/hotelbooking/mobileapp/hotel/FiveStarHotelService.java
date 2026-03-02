package com.hotelbooking.mobileapp.hotel;

import java.util.List;
import java.util.Optional;

public interface FiveStarHotelService {

    FiveStarHotel save(FiveStarHotel hotel);

    List<FiveStarHotel> getAll();

    Optional<FiveStarHotel> getById(String registrationId);

    List<FiveStarHotel> getByVendorId(String vendorId);

    List<FiveStarHotel> getByStatus(String status);

    FiveStarHotel update(String registrationId, FiveStarHotel hotel);

    void delete(String registrationId);
}