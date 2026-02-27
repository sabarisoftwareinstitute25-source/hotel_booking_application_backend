package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface GlobalEliteHotelService {

    GlobalEliteHotel create(GlobalEliteHotel hotel);

    GlobalEliteHotel getByRegistrationId(String registrationId);

    List<GlobalEliteHotel> getAll();

    GlobalEliteHotel update(String registrationId, GlobalEliteHotel hotel);

    void delete(String registrationId);
}