package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/normal-hotels")
@RequiredArgsConstructor
public class HotelVendorController {

    private final HotelVendorService service; // inject the correct service

    @PostMapping
    public ResponseEntity<HotelVendor> saveHotel(@RequestBody HotelVendor hotel) {
        HotelVendor saved = service.saveHotel(hotel);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<HotelVendor>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<HotelVendor> getHotelById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getHotelById(registrationId));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.noContent().build();
    }
}