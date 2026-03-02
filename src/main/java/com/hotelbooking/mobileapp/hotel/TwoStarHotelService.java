package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface TwoStarHotelService {

    TwoStarHotel saveHotelVendor(TwoStarHotel request);

    TwoStarHotel registerHotel(TwoStarHotel twoStarHotel);

    TwoStarHotel getByRegistrationId(String registrationId);

    List<TwoStarHotel> getAllHotels();

    List<TwoStarHotel> getByVendor(String vendorId);

    TwoStarHotel updateHotel(String registrationId, TwoStarHotel twoStarHotel);

    void deleteHotel(String registrationId);
}