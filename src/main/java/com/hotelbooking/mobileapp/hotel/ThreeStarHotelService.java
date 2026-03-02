package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface ThreeStarHotelService {

    ThreeStarHotel registerHotel(ThreeStarHotel request);

    ThreeStarHotel getById(String registrationId);

    List<ThreeStarHotel> getAllHotels();

    List<ThreeStarHotel> getByVendor(String vendorId);

    List<ThreeStarHotel> getByStatus(String status);

    ThreeStarHotel updateHotel(String registrationId, ThreeStarHotel hotel);

    void deleteHotel(String registrationId);
}