package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.dto.FourStarHotelProfileDTO;
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

    @GetMapping("/{hotelId}")
    public ResponseEntity<FourStarHotel> getHotelById(@PathVariable String hotelId) {
        return ResponseEntity.ok(service.getHotelById(hotelId));
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String hotelId) {
        service.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

    // Get Full Profile
    @GetMapping("/profile/{hotelId}")
    public FourStarHotelProfileDTO getHotelProfile(@PathVariable String hotelId) {

        return service.getHotelProfile(hotelId);
    }
}