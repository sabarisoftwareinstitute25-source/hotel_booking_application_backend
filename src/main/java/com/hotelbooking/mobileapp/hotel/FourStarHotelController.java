package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/four-star-hotels")
@RequiredArgsConstructor
public class FourStarHotelController {

    private final FourStarHotelService service;

    @PostMapping
    public ResponseEntity<FourStarHotel> register(@RequestBody FourStarHotel hotel) {
        return ResponseEntity.ok(service.registerHotel(hotel));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<FourStarHotel> getById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getById(registrationId));
    }

    @GetMapping
    public ResponseEntity<List<FourStarHotel>> getAll() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<FourStarHotel>> getByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(service.getByVendor(vendorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FourStarHotel>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<FourStarHotel> update(
            @PathVariable String registrationId,
            @RequestBody FourStarHotel hotel) {
        return ResponseEntity.ok(service.updateHotel(registrationId, hotel));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> delete(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.ok("Four Star Hotel deleted successfully");
    }
}