package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImp implements VendorService {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor createVendor(Vendor vendor) {

        if (vendorRepository.existsByEmail(vendor.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (vendorRepository.existsByPhone(vendor.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }

        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor getVendorById(String vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor updateVendor(String vendorId, Vendor updatedVendor) {

        Vendor existing = getVendorById(vendorId);

        existing.setFullName(updatedVendor.getFullName());
        existing.setBusinessName(updatedVendor.getBusinessName());
        existing.setPhone(updatedVendor.getPhone());
        existing.setEmail(updatedVendor.getEmail());
        existing.setStatus(updatedVendor.getStatus());

        return vendorRepository.save(existing);
    }

    @Override
    public void deleteVendor(String vendorId) {
        vendorRepository.deleteById(vendorId);
    }
}