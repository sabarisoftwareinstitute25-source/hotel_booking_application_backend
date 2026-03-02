package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface SixStarHotelService {

    SixStarHotel registerHotel(SixStarHotel hotel);

    SixStarHotel getById(String registrationId);

    List<SixStarHotel> getAllHotels();

    List<SixStarHotel> getByVendor(String vendorId);

    List<SixStarHotel> getByStatus(String status);

    SixStarHotel updateHotel(String registrationId, SixStarHotel hotel);

    void deleteHotel(String registrationId);
}