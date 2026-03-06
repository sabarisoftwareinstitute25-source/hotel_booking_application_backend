package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
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

    // Save Hotel
    @PostMapping
    public ResponseEntity<TwoStarHotel> saveHotel(@RequestBody TwoStarHotel hotel) {
        return ResponseEntity.ok(service.saveHotel(hotel));
    }

    // Get All
    @GetMapping
    public ResponseEntity<List<TwoStarHotel>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    // Get By ID
    @GetMapping("/{hotelId}")
    public ResponseEntity<TwoStarHotel> getHotel(@PathVariable String hotelId) {
        return service.getHotelById(hotelId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable String hotelId) {
        service.deleteHotel(hotelId);
        return ResponseEntity.ok("Deleted Successfully");
    }
}