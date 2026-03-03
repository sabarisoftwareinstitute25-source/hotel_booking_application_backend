package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/global-elite-hotels")
@RequiredArgsConstructor
@CrossOrigin
public class GlobalEliteHotelController {

    private final GlobalEliteHotelService service; // inject the correct service

    @PostMapping
    public ResponseEntity<GlobalEliteHotel> saveHotel(@RequestBody GlobalEliteHotel hotel) {
        GlobalEliteHotel saved = service.saveHotel(hotel);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<GlobalEliteHotel>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<GlobalEliteHotel> getHotelById(@PathVariable String registrationId) {
        return ResponseEntity.ok(service.getHotelById(registrationId));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return ResponseEntity.noContent().build();
    }
}