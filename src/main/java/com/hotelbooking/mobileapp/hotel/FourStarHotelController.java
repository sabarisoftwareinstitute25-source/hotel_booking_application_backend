package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/four-star-hotels")
@RequiredArgsConstructor
public class FourStarHotelController {

    private final FourStarHotelService service; // inject the correct service

    @PostMapping
    public ResponseEntity<FourStarHotel> saveHotel(@RequestBody FourStarHotel hotel) {
        FourStarHotel saved = service.saveHotel(hotel);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<FourStarHotel>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<FourStarHotel> getHotelById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getHotelById(registrationId));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.noContent().build();
    }
}