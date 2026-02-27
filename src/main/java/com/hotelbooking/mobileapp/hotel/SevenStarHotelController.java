package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seven-star-hotels")
@RequiredArgsConstructor
@CrossOrigin
public class SevenStarHotelController {

    private final SevenStarHotelService service;

    @PostMapping
    public SevenStarHotel createHotel(@RequestBody SevenStarHotel hotel) {
        return service.createHotel(hotel);
    }

    @GetMapping("/{registrationId}")
    public SevenStarHotel getHotel(@PathVariable String registrationId) {
        return service.getByRegistrationId(registrationId);
    }

    @GetMapping
    public List<SevenStarHotel> getAllHotels() {
        return service.getAllHotels();
    }

    @PutMapping("/{registrationId}")
    public SevenStarHotel updateHotel(
            @PathVariable String registrationId,
            @RequestBody SevenStarHotel hotel) {
        return service.updateHotel(registrationId, hotel);
    }

    @DeleteMapping("/{registrationId}")
    public String deleteHotel(@PathVariable String registrationId) {
        service.deleteHotel(registrationId);
        return "Hotel deleted successfully";
    }
}