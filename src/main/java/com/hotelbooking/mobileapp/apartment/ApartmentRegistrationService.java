package com.hotelbooking.mobileapp.apartment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentRegistrationService {

    private final ApartmentRegistrationRepository repository;

    public ApartmentRegistration save(ApartmentRegistration apartment){
        return repository.save(apartment);
    }

    public List<ApartmentRegistration> getAll(){
        return repository.findAll();
    }

    public ApartmentRegistration getById(String apartmentId){
        return repository.findById(apartmentId).orElseThrow();
    }

    public void delete(String apartmentId){
        repository.deleteById(apartmentId);
    }
}