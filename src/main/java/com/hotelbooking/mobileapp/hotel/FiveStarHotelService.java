package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface FiveStarHotelService {

    FiveStarHotel registerHotel(FiveStarHotel hotel);

    FiveStarHotel getById(String registrationId);

    List<FiveStarHotel> getAllHotels();

    List<FiveStarHotel> getByVendor(String vendorId);

    List<FiveStarHotel> getByStatus(String status);

    FiveStarHotel updateHotel(String registrationId, FiveStarHotel hotel);

    void deleteHotel(String registrationId);
}