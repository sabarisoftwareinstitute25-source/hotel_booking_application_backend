package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/six-star-hotels")
@RequiredArgsConstructor
public class SixStarHotelController {

    private final SixStarHotelService service;

    @PostMapping
    public ResponseEntity<SixStarHotel> register(@RequestBody SixStarHotel hotel) {
        return ResponseEntity.ok(service.registerHotel(hotel));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<SixStarHotel> getById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getById(registrationId));
    }

    @GetMapping
    public ResponseEntity<List<SixStarHotel>> getAll() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<SixStarHotel>> getByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(service.getByVendor(vendorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SixStarHotel>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<SixStarHotel> update(
            @PathVariable String registrationId,
            @RequestBody SixStarHotel hotel) {
        return ResponseEntity.ok(service.updateHotel(registrationId, hotel));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> delete(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.ok("Six Star Hotel deleted successfully");
    }
}