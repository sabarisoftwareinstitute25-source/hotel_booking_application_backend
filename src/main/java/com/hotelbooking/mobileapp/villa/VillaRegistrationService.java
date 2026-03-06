package com.hotelbooking.mobileapp.villa;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VillaRegistrationService {

    private final VillaRegistrationRepository repository;

    public VillaRegistrationService(VillaRegistrationRepository repository) {
        this.repository = repository;
    }

    public VillaRegistration registerVilla(VillaRegistration villa) {
        return repository.save(villa);
    }

    public List<VillaRegistration> getAll(){
        return repository.findAll();
    }

    public VillaRegistration getById(String villaId){
        return repository.findById(villaId)
                .orElseThrow(() -> new RuntimeException("Villa not found"));
    }

    public void delete(String villaId){
        repository.deleteById(villaId);
    }
}
