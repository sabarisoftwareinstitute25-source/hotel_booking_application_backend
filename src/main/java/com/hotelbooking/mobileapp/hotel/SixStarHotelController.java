package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/six-star-hotels")
@RequiredArgsConstructor
public class SixStarHotelController {

    private final SixStarHotelService service; // inject the correct service

    @PostMapping
    public ResponseEntity<SixStarHotel> saveHotel(@RequestBody SixStarHotel hotel) {
        SixStarHotel saved = service.saveHotel(hotel);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<SixStarHotel>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<SixStarHotel> getHotelById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getHotelById(registrationId));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.noContent().build();
    }
}