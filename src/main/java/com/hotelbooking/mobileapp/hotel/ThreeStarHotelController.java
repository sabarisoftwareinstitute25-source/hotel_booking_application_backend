package com.hotelbooking.mobileapp.hotel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/three-star-hotels")
public class ThreeStarHotelController {

    private final ThreeStarHotelService service;

    public ThreeStarHotelController(ThreeStarHotelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ThreeStarHotel> create(@RequestBody ThreeStarHotel hotel) {
        return ResponseEntity.ok(service.save(hotel));
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<ThreeStarHotel> update(
            @PathVariable String registrationId,
            @RequestBody ThreeStarHotel hotel) {
        return ResponseEntity.ok(service.update(registrationId, hotel));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<ThreeStarHotel> getById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getById(registrationId));
    }

    @GetMapping
    public ResponseEntity<List<ThreeStarHotel>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> delete(@PathVariable String registrationId) {
        service.delete(registrationId);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<ThreeStarHotel>> getByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(service.getByVendor(vendorId));
    }
}