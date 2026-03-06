package com.hotelbooking.mobileapp.resort;

import com.hotelbooking.mobileapp.util.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resort")
public class ResortRegistrationController {
    private final ResortRegistrationService service;
    private final FileStorageService fileStorageService;

    public ResortRegistrationController(ResortRegistrationService service,
                                       FileStorageService fileStorageService) {
        this.service = service;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResortRegistration> registerResort(
            @RequestBody ResortRegistration resort) {

        ResortRegistration savedResort = service.registerResort(resort);
        return ResponseEntity.ok(savedResort);
    }

    @GetMapping
    public List<ResortRegistration> getAll(){
        return service.getAll();
    }

    @GetMapping("/{resortId}")
    public ResortRegistration getResort(@PathVariable String resortId){
        return service.getById(resortId);
    }

    @DeleteMapping("/{resortId}")
    public void deleteResort(@PathVariable String resortId){
        service.delete(resortId);
    }
}
