package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.exception.EmailAlreadyExistsException;
import com.hotelbooking.mobileapp.util.IdGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImp implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;



    @Override
    public Vendor createVendor(Vendor vendor) {

        String vendorId = idGeneratorService.generateVendorRegistrationId();
        vendor.setVendorId(vendorId);

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