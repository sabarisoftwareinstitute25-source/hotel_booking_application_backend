package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface SevenStarHotelService {

    SevenStarHotel createHotel(SevenStarHotel hotel);

    SevenStarHotel getByRegistrationId(String registrationId);

    List<SevenStarHotel> getAllHotels();

    SevenStarHotel updateHotel(String registrationId, SevenStarHotel hotel);

    void deleteHotel(String registrationId);
}