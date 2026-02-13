package com.hotelbooking.mobileapp.hotel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/two-star-hotels")
public class TwoStarHotelController {

    private final TwoStarHotelService service;

    public TwoStarHotelController(TwoStarHotelService service) {
        this.service = service;
    }

    // 🔹 Create / Register hotel
    @PostMapping
    public ResponseEntity<TwoStarHotel> createHotel(@RequestBody TwoStarHotel hotel) {
        return new ResponseEntity<>(service.saveHotel(hotel), HttpStatus.CREATED);
    }

    // 🔹 Update hotel
    @PutMapping("/{registrationId}")
    public ResponseEntity<TwoStarHotel> updateHotel(
            @PathVariable String registrationId,
            @RequestBody TwoStarHotel hotel) {
        return ResponseEntity.ok(service.updateHotel(registrationId, hotel));
    }

    // 🔹 Get by registrationId
    @GetMapping("/{registrationId}")
    public ResponseEntity<TwoStarHotel> getByRegistrationId(
            @PathVariable String registrationId) {
        return ResponseEntity.ok(service.getByRegistrationId(registrationId));
    }

    // 🔹 Get by hotelId
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<TwoStarHotel> getByHotelId(
            @PathVariable String hotelId) {
        return ResponseEntity.ok(service.getByHotelId(hotelId));
    }

    // 🔹 Get by vendorId
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<TwoStarHotel>> getByVendorId(
            @PathVariable String vendorId) {
        return ResponseEntity.ok(service.getByVendorId(vendorId));
    }

    // 🔹 Get all hotels
    @GetMapping
    public ResponseEntity<List<TwoStarHotel>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    // 🔹 Delete hotel
    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteHotel(
            @PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.noContent().build();
    }
}
