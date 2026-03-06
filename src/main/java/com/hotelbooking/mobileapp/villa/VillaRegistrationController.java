package com.hotelbooking.mobileapp.villa;

import com.hotelbooking.mobileapp.util.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/villas")
public class VillaRegistrationController {

    private final VillaRegistrationService service;
    private final FileStorageService fileStorageService;

    public VillaRegistrationController(VillaRegistrationService service,
                                       FileStorageService fileStorageService) {
        this.service = service;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/register")
    public ResponseEntity<VillaRegistration> registerVilla(
            @RequestBody VillaRegistration villa) {

        VillaRegistration savedVilla = service.registerVilla(villa);
        return ResponseEntity.ok(savedVilla);
    }

    @GetMapping
    public List<VillaRegistration> getAll(){
        return service.getAll();
    }

    @GetMapping("/{villaId}")
    public VillaRegistration getVilla(@PathVariable String villaId){
        return service.getById(villaId);
    }

    @DeleteMapping("/{villaId}")
    public void deleteVilla(@PathVariable String villaId){
        service.delete(villaId);
    }
}