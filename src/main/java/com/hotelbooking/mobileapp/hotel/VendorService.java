package com.hotelbooking.mobileapp.hotel;

import java.util.List;

public interface VendorService {

    Vendor createVendor(Vendor vendor);

    Vendor getVendorById(String vendorId);

    List<Vendor> getAllVendors();

    Vendor updateVendor(String vendorId, Vendor vendor);

    void deleteVendor(String vendorId);
}