package com.hotelbooking.mobileapp.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, String> {

    Optional<Vendor> findByEmail(String email);

    Optional<Vendor> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}