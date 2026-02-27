package com.hotelbooking.mobileapp.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/global-elite-hotels")
@RequiredArgsConstructor
@CrossOrigin
public class GlobalEliteHotelController {

    private final GlobalEliteHotelService service;

    @PostMapping
    public GlobalEliteHotel create(@RequestBody GlobalEliteHotel hotel) {
        return service.create(hotel);
    }

    @GetMapping("/{registrationId}")
    public GlobalEliteHotel getById(@PathVariable String registrationId) {
        return service.getByRegistrationId(registrationId);
    }

    @GetMapping
    public List<GlobalEliteHotel> getAll() {
        return service.getAll();
    }

    @PutMapping("/{registrationId}")
    public GlobalEliteHotel update(
            @PathVariable String registrationId,
            @RequestBody GlobalEliteHotel hotel) {
        return service.update(registrationId, hotel);
    }

    @DeleteMapping("/{registrationId}")
    public String delete(@PathVariable String registrationId) {
        service.delete(registrationId);
        return "Global Elite Hotel deleted successfully";
    }
}