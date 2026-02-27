package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/five-star-hotels")
@RequiredArgsConstructor
public class FiveStarHotelController {

    private final FiveStarHotelService service;

    @PostMapping
    public ResponseEntity<FiveStarHotel> register(@RequestBody FiveStarHotel hotel) {
        return ResponseEntity.ok(service.registerHotel(hotel));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<FiveStarHotel> getById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getById(registrationId));
    }

    @GetMapping
    public ResponseEntity<List<FiveStarHotel>> getAll() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<FiveStarHotel>> getByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(service.getByVendor(vendorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FiveStarHotel>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<FiveStarHotel> update(
            @PathVariable String registrationId,
            @RequestBody FiveStarHotel hotel) {
        return ResponseEntity.ok(service.updateHotel(registrationId, hotel));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> delete(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.ok("Five Star Hotel deleted successfully");
    }
}