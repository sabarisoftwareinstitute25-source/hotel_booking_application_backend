package com.hotelbooking.mobileapp.apartment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
@CrossOrigin
public class ApartmentRegistrationController {

    private final ApartmentRegistrationService service;

    @PostMapping
    public ApartmentRegistration create(@RequestBody ApartmentRegistration apartment){
        return service.save(apartment);
    }

    @GetMapping
    public List<ApartmentRegistration> getAll(){
        return service.getAll();
    }

    @GetMapping("/{apartmentId}")
    public ApartmentRegistration getById(@PathVariable String apartmentId){
        return service.getById(apartmentId);
    }

    @DeleteMapping("/{apartmentId}")
    public void delete(@PathVariable String apartmentId){
        service.delete(apartmentId);
    }
}