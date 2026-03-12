package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.dto.FiveStarHotelProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/five-star-hotels")
@RequiredArgsConstructor
public class FiveStarHotelController {

    private final FiveStarHotelService service; // inject the correct service

    @PostMapping
    public ResponseEntity<FiveStarHotel> saveHotel(@RequestBody FiveStarHotel hotel) {
        FiveStarHotel saved = service.saveHotel(hotel);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<FiveStarHotel>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<FiveStarHotel> getHotelById(@PathVariable String hotelId) {
        return ResponseEntity.ok(service.getHotelById(hotelId));
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String hotelId) {
        service.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile/{hotelId}")
    public FiveStarHotelProfileDTO getHotelProfile(@PathVariable String hotelId) {
        return service.getHotelProfile(hotelId);
    }
}