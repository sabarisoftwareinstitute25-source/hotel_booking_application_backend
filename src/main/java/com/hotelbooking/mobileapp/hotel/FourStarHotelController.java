package com.hotelbooking.mobileapp.hotel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/four-star-hotels")
@CrossOrigin
public class FourStarHotelController {

    private final FourStarHotelService service;

    public FourStarHotelController(FourStarHotelService service) {
        this.service = service;
    }

    // Create
    @PostMapping
    public ResponseEntity<FourStarHotel> create(@RequestBody FourStarHotel hotel) {
        return ResponseEntity.ok(service.save(hotel));
    }

    // Update
    @PutMapping("/{registrationId}")
    public ResponseEntity<FourStarHotel> update(
            @PathVariable String registrationId,
            @RequestBody FourStarHotel hotel) {

        return ResponseEntity.ok(service.update(registrationId, hotel));
    }

    //get by id
    @GetMapping("/{registrationId}")
    public ResponseEntity<FourStarHotel> getById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getById(registrationId));
    }

    // Get all
    @GetMapping
    public ResponseEntity<List<FourStarHotel>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Delete
    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> delete(@PathVariable String registrationId) {
        service.delete(registrationId);
        return ResponseEntity.noContent().build();
    }

    // Get by Vendor
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<FourStarHotel>> getByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(service.getByVendor(vendorId));
    }
}