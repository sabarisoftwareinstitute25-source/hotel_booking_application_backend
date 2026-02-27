package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface FourStarHotelService {

    FourStarHotel registerHotel(FourStarHotel hotel);

    FourStarHotel getById(String registrationId);

    List<FourStarHotel> getAllHotels();

    List<FourStarHotel> getByVendor(String vendorId);

    List<FourStarHotel> getByStatus(String status);

    FourStarHotel updateHotel(String registrationId, FourStarHotel hotel);

    void deleteHotel(String registrationId);
}