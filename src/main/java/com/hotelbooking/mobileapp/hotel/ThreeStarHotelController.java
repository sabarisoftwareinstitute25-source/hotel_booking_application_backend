package com.hotelbooking.mobileapp.hotel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/three-star-hotels")
@CrossOrigin(origins = "*")
public class ThreeStarHotelController {

    private final ThreeStarHotelService service;

    public ThreeStarHotelController(ThreeStarHotelService service) {
        this.service = service;
    }

    // Save Hotel
    @PostMapping
    public ResponseEntity<ThreeStarHotel> saveHotel(@RequestBody ThreeStarHotel hotel) {
        return ResponseEntity.ok(service.saveHotel(hotel));
    }

    // Get All
    @GetMapping
    public ResponseEntity<List<ThreeStarHotel>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    // Get By ID
    @GetMapping("/{hotelId}")
    public ResponseEntity<ThreeStarHotel> getHotel(@PathVariable String hotelId) {
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