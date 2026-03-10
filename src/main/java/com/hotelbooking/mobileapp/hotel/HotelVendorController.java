package com.hotelbooking.mobileapp.hotel;

import com.hotelbooking.mobileapp.dto.NormalHotelVendorProfileDTO;
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

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelVendor> getHotelById(@PathVariable String hotelId) {
        return ResponseEntity.ok(service.getHotelById(hotelId));
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String hotelId) {
        service.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile/{hotelId}")
    public NormalHotelVendorProfileDTO getProfile(@PathVariable String hotelId) {
        return service.getHotelProfile(hotelId);
    }
}