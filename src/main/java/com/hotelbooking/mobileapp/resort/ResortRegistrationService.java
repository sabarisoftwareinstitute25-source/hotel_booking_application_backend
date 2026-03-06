package com.hotelbooking.mobileapp.resort;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResortRegistrationService {
    private final ResortRegistrationRepository repository;

    public ResortRegistrationService(ResortRegistrationRepository repository) {
        this.repository = repository;
    }

    public ResortRegistration registerResort(ResortRegistration resort) {
        return repository.save(resort);
    }

    public List<ResortRegistration> getAll(){
        return repository.findAll();
    }

    public ResortRegistration getById(String resortId){
        return repository.findById(resortId)
                .orElseThrow(() -> new RuntimeException("resort not found"));
    }

    public void delete(String resortId){
        repository.deleteById(resortId);
    }
}
