package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seven-star-hotels")
@RequiredArgsConstructor
@CrossOrigin
public class SevenStarHotelController {

    private final SevenStarHotelService service; // inject the correct service

    @PostMapping
    public ResponseEntity<SevenStarHotel> saveHotel(@RequestBody SevenStarHotel hotel) {
        SevenStarHotel saved = service.saveHotel(hotel);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<SevenStarHotel>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<SevenStarHotel> getHotelById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getHotelById(registrationId));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.noContent().build();
    }
}