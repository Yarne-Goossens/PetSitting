package be.petsitgroup.petsitting.controller;

import be.petsitgroup.petsitting.dto.pets.CreatePetsRequest;
import be.petsitgroup.petsitting.dto.pets.PetsResponse;
import be.petsitgroup.petsitting.service.PetsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@SecurityRequirement(name = "bearerAuth")
public class PetsController {

    private final PetsService petsService;

    public PetsController(PetsService petsService) {
        this.petsService = petsService;
    }

    @PostMapping
    public ResponseEntity<PetsResponse> createPet(@RequestBody CreatePetsRequest request) {
        return ResponseEntity.ok(petsService.createPetForCurrentOwner(request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PetsResponse>> getMyPets() {
        return ResponseEntity.ok(petsService.getMyPets());
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<PetsResponse> updateAvailability(
            @PathVariable Long id,
            @RequestParam("available") boolean available) {

        return ResponseEntity.ok(petsService.updateAvailability(id, available));
    }
}
