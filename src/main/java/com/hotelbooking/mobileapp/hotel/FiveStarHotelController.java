package com.hotelbooking.mobileapp.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/five-star-hotels")
@CrossOrigin(origins = "*")
public class FiveStarHotelController {

    @Autowired
    private FiveStarHotelService service;

    // Create
    @PostMapping
    public FiveStarHotel create(@RequestBody FiveStarHotel hotel) {
        return service.save(hotel);
    }

    // Get All
    @GetMapping
    public List<FiveStarHotel> getAll() {
        return service.getAll();
    }

    // Get By ID
    @GetMapping("/{id}")
    public Optional<FiveStarHotel> getById(@PathVariable String id) {
        return service.getById(id);
    }

    // Get By Vendor
    @GetMapping("/vendor/{vendorId}")
    public List<FiveStarHotel> getByVendor(@PathVariable String vendorId) {
        return service.getByVendorId(vendorId);
    }

    // Get By Status
    @GetMapping("/status/{status}")
    public List<FiveStarHotel> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    // Update
    @PutMapping("/{id}")
    public FiveStarHotel update(
            @PathVariable String id,
            @RequestBody FiveStarHotel hotel) {
        return service.update(id, hotel);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Five Star Hotel deleted successfully";
    }
}