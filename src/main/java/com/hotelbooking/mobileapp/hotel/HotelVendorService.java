package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface HotelVendorService {

    HotelVendor registerHotel(HotelVendor hotelVendor);

    HotelVendor getByRegistrationId(String registrationId);

    List<HotelVendor> getAllHotels();

    List<HotelVendor> getByVendor(String vendorId);

    HotelVendor updateHotel(String registrationId, HotelVendor hotelVendor);

    void deleteHotel(String registrationId);
}