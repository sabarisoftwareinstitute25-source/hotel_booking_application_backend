package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, String> {
    Optional<Vendor> findByEmail(String email);
    Optional<Vendor> findByPhone(String phone);
    Optional<Vendor> findByVendorId(String vendorId);
    
    // Alias for findByPhone (for backward compatibility)
    default Optional<Vendor> findByMobileNumber(String phone) {
        return findByPhone(phone);
    }
}

