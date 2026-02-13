package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface TwoStarHotelService {

    TwoStarHotel saveHotel(TwoStarHotel hotel);

    TwoStarHotel updateHotel(String registrationId, TwoStarHotel hotel);

    TwoStarHotel getByRegistrationId(String registrationId);

    TwoStarHotel getByHotelId(String hotelId);

    List<TwoStarHotel> getByVendorId(String vendorId);

    List<TwoStarHotel> getAllHotels();

    void deleteHotel(String registrationId);
}