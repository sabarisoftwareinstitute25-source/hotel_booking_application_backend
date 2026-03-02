package com.hotelbooking.mobileapp.hotel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/two-star-hotels")
public class TwoStarHotelController {

    private final TwoStarHotelService hotelService;

    public TwoStarHotelController(TwoStarHotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<TwoStarHotel> registerHotel(@RequestBody TwoStarHotel hotel) {
        return ResponseEntity.ok(hotelService.registerHotel(hotel));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<TwoStarHotel> getByRegistrationId(@PathVariable String registrationId) {
        return ResponseEntity.ok(hotelService.getByRegistrationId(registrationId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TwoStarHotel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<TwoStarHotel>> getByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(hotelService.getByVendor(vendorId));
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<TwoStarHotel> updateHotel(
            @PathVariable String registrationId,
            @RequestBody TwoStarHotel hotel) {
        return ResponseEntity.ok(hotelService.updateHotel(registrationId, hotel));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String registrationId) {
        hotelService.deleteHotel(registrationId);
        return ResponseEntity.ok().build();
    }
}