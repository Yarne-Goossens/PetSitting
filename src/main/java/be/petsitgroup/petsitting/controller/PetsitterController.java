package be.petsitgroup.petsitting.controller;

import be.petsitgroup.petsitting.dto.CreateOrUpdatePetsitterRequest;
import be.petsitgroup.petsitting.dto.PetsitterResponse;
import be.petsitgroup.petsitting.service.PetsitterService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petsitters")
@SecurityRequirement(name = "bearerAuth")
public class PetsitterController {

    private final PetsitterService petsitterService;

    public PetsitterController(PetsitterService petsitterService) {
        this.petsitterService = petsitterService;
    }

    // create or update current user's petsitter profile
    @PostMapping("/me")
    public ResponseEntity<PetsitterResponse> createOrUpdateMyProfile(
            @RequestBody CreateOrUpdatePetsitterRequest request) {

        return ResponseEntity.ok(petsitterService.createOrUpdateMyProfile(request));
    }

    // get current user's petsitter profile
    @GetMapping("/me")
    public ResponseEntity<PetsitterResponse> getMyProfile() {
        return ResponseEntity.ok(petsitterService.getMyProfile());
    }

    // list all petsitters
    @GetMapping
    public ResponseEntity<List<PetsitterResponse>> getAllPetsitters() {
        return ResponseEntity.ok(petsitterService.getAllPetsitters());
    }
}
