package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface ThreeStarHotelService {

    ThreeStarHotel save(ThreeStarHotel hotel);

    ThreeStarHotel update(String registrationId, ThreeStarHotel hotel);

    ThreeStarHotel getById(String registrationId);

    List<ThreeStarHotel> getAll();

    void delete(String registrationId);

    List<ThreeStarHotel> getByVendor(String vendorId);
}