package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel-vendors")
@RequiredArgsConstructor
public class HotelVendorController {

    private final HotelVendorService service;

    @PostMapping
    public ResponseEntity<HotelVendor> register(@RequestBody HotelVendor hotelVendor) {
        return ResponseEntity.ok(service.registerHotel(hotelVendor));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<HotelVendor> getById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getByRegistrationId(registrationId));
    }

    @GetMapping
    public ResponseEntity<List<HotelVendor>> getAll() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<HotelVendor>> getByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(service.getByVendor(vendorId));
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<HotelVendor> update(
            @PathVariable String registrationId,
            @RequestBody HotelVendor hotelVendor) {
        return ResponseEntity.ok(service.updateHotel(registrationId, hotelVendor));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> delete(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.ok("Hotel deleted successfully");
    }
}