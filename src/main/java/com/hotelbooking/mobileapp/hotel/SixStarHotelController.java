package com.hotelbooking.mobileapp.hotel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/six_star_hotels")
@CrossOrigin(origins = "*")
public class SixStarHotelController {

    private final SixStarHotelService service;

    public SixStarHotelController(SixStarHotelService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<SixStarHotel> create(@RequestBody SixStarHotel hotel) {
        return ResponseEntity.ok(service.save(hotel));
    }

    // UPDATE
    @PutMapping("/{registrationId}")
    public ResponseEntity<SixStarHotel> update(
            @PathVariable String registrationId,
            @RequestBody SixStarHotel hotel) {

        return ResponseEntity.ok(service.update(registrationId, hotel));
    }

    // GET BY ID
    @GetMapping("/{registrationId}")
    public ResponseEntity<SixStarHotel> getById(
            @PathVariable String registrationId) {

        return ResponseEntity.ok(service.getById(registrationId));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<SixStarHotel>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // DELETE
    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> delete(
            @PathVariable String registrationId) {

        service.delete(registrationId);
        return ResponseEntity.ok("Deleted Successfully");
    }

    // GET BY VENDOR
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<SixStarHotel>> getByVendor(
            @PathVariable String vendorId) {

        return ResponseEntity.ok(service.getByVendor(vendorId));
    }
}