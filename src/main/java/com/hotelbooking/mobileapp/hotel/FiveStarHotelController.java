package com.hotelbooking.mobileapp.hotel;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/five-star-hotels")
@CrossOrigin
public class FiveStarHotelController {

    private final FiveStarHotelService service;

    public FiveStarHotelController(FiveStarHotelService service) {
        this.service = service;
    }

    @PostMapping
    public FiveStarHotel create(@RequestBody FiveStarHotel hotel) {
        return service.save(hotel);
    }

    @PutMapping("/{registrationId}")
    public FiveStarHotel update(@PathVariable String registrationId,
                                @RequestBody FiveStarHotel hotel) {
        return service.update(registrationId, hotel);
    }

    @GetMapping("/{registrationId}")
    public FiveStarHotel getById(@PathVariable String registrationId) {
        return service.getById(registrationId);
    }

    @GetMapping
    public List<FiveStarHotel> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{registrationId}")
    public void delete(@PathVariable String registrationId) {
        service.delete(registrationId);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<FiveStarHotel> getByVendor(@PathVariable String vendorId) {
        return service.getByVendor(vendorId);
    }
}