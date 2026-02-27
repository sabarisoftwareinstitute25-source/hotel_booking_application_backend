package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/three-star-hotels")
@RequiredArgsConstructor
public class ThreeStarHotelController {

    private final ThreeStarHotelService service;

    @PostMapping
    public ResponseEntity<ThreeStarHotel> register(@RequestBody ThreeStarHotel hotel) {
        return ResponseEntity.ok(service.registerHotel(hotel));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<ThreeStarHotel> getById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getById(registrationId));
    }

    @GetMapping
    public ResponseEntity<List<ThreeStarHotel>> getAll() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<ThreeStarHotel>> getByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(service.getByVendor(vendorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ThreeStarHotel>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<ThreeStarHotel> update(
            @PathVariable String registrationId,
            @RequestBody ThreeStarHotel hotel) {
        return ResponseEntity.ok(service.updateHotel(registrationId, hotel));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> delete(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.ok("Three Star Hotel deleted successfully");
    }
}