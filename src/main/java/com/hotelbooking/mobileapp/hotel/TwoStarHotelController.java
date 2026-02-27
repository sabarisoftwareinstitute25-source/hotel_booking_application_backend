package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/two-star-hotels")
@RequiredArgsConstructor
public class TwoStarHotelController {

    private final TwoStarHotelService service;

    @PostMapping
    public ResponseEntity<TwoStarHotel> register(@RequestBody TwoStarHotel hotel) {
        return ResponseEntity.ok(service.registerHotel(hotel));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<TwoStarHotel> getById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getById(registrationId));
    }

    @GetMapping
    public ResponseEntity<List<TwoStarHotel>> getAll() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<TwoStarHotel>> getByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(service.getByVendor(vendorId));
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<TwoStarHotel> update(
            @PathVariable String registrationId,
            @RequestBody TwoStarHotel hotel) {
        return ResponseEntity.ok(service.updateHotel(registrationId, hotel));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> delete(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.ok("Two Star Hotel deleted successfully");
    }
}