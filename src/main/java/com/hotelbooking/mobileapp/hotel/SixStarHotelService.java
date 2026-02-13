package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface SixStarHotelService {

    SixStarHotel save(SixStarHotel hotel);

    SixStarHotel update(String registrationId, SixStarHotel hotel);

    SixStarHotel getById(String registrationId);

    List<SixStarHotel> getAll();

    void delete(String registrationId);

    List<SixStarHotel> getByVendor(String vendorId);
}