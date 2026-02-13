package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface FiveStarHotelService {

    FiveStarHotel save(FiveStarHotel hotel);

    FiveStarHotel update(String registrationId, FiveStarHotel hotel);

    FiveStarHotel getById(String registrationId);

    List<FiveStarHotel> getAll();

    void delete(String registrationId);

    List<FiveStarHotel> getByVendor(String vendorId);
}