package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
        return ResponseEntity.ok(vendorService.createVendor(vendor));
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<Vendor> getVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(vendorService.getVendorById(vendorId));
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    @PutMapping("/{vendorId}")
    public ResponseEntity<Vendor> updateVendor(
            @PathVariable String vendorId,
            @RequestBody Vendor vendor) {
        return ResponseEntity.ok(vendorService.updateVendor(vendorId, vendor));
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<String> deleteVendor(@PathVariable String vendorId) {
        vendorService.deleteVendor(vendorId);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}