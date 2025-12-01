package be.petsitgroup.petsitting.controller;

import be.petsitgroup.petsitting.dto.CreatePlaydateRequest;
import be.petsitgroup.petsitting.dto.PlaydateResponse;
import be.petsitgroup.petsitting.dto.UpdatePlaydateStatusRequest;
import be.petsitgroup.petsitting.service.PlaydateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playdates")
@SecurityRequirement(name = "bearerAuth")
public class PlaydateController {

    private final PlaydateService playdateService;

    public PlaydateController(PlaydateService playdateService) {
        this.playdateService = playdateService;
    }

    // owner creates a playdate for their pet
    @PostMapping
    public ResponseEntity<PlaydateResponse> createPlaydate(
            @RequestBody CreatePlaydateRequest request) {
        return ResponseEntity.ok(playdateService.createPlaydate(request));
    }

    // playdates where I'm the pet owner
    @GetMapping("/owner/me")
    public ResponseEntity<List<PlaydateResponse>> getMyPlaydatesAsOwner() {
        return ResponseEntity.ok(playdateService.getMyPlaydatesAsOwner());
    }

    // playdates where I'm the petsitter
    @GetMapping("/petsitter/me")
    public ResponseEntity<List<PlaydateResponse>> getMyPlaydatesAsPetsitter() {
        return ResponseEntity.ok(playdateService.getMyPlaydatesAsPetsitter());
    }

    // petsitter updates status: REQUESTED -> CONFIRMED/COMPLETED/CANCELLED
    @PatchMapping("/{id}/status")
    public ResponseEntity<PlaydateResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdatePlaydateStatusRequest request) {
        return ResponseEntity.ok(playdateService.updateStatus(id, request));
    }
}
