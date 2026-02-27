package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface TwoStarHotelService {

    TwoStarHotel registerHotel(TwoStarHotel hotel);

    TwoStarHotel getById(String registrationId);

    List<TwoStarHotel> getAllHotels();

    List<TwoStarHotel> getByVendor(String vendorId);

    TwoStarHotel updateHotel(String registrationId, TwoStarHotel hotel);

    void deleteHotel(String registrationId);
}