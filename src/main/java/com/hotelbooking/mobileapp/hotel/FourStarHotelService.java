package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface FourStarHotelService {

    FourStarHotel save(FourStarHotel hotel);

    FourStarHotel update(String registrationId, FourStarHotel hotel);

    FourStarHotel getById(String registrationId);

    List<FourStarHotel> getAll();

    void delete(String registrationId);

    List<FourStarHotel> getByVendor(String vendorId);
}