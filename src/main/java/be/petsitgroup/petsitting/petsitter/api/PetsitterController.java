package be.petsitgroup.petsitting.petsitter.api;

import be.petsitgroup.petsitting.dto.petsitter.CreateOrUpdatePetsitterRequest;
import be.petsitgroup.petsitting.dto.petsitter.PetsitterResponse;
import be.petsitgroup.petsitting.petsitter.application.command.CreateOrUpdatePetsitterCommand;
import be.petsitgroup.petsitting.petsitter.application.command.PetsitterCommandService;
import be.petsitgroup.petsitting.petsitter.application.query.PetsitterQueryService;
import be.petsitgroup.petsitting.petsitter.application.query.PetsitterView;
import be.petsitgroup.petsitting.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/petsitters")
@SecurityRequirement(name = "bearerAuth")
public class PetsitterController {

    private final PetsitterCommandService petsitterCommandService;
    private final PetsitterQueryService petsitterQueryService;

    public PetsitterController(PetsitterCommandService petsitterCommandService,
            PetsitterQueryService petsitterQueryService) {
        this.petsitterCommandService = petsitterCommandService;
        this.petsitterQueryService = petsitterQueryService;
    }

    // create or update MY petsitter profile
    @PostMapping("/me")
    public ResponseEntity<PetsitterResponse> createOrUpdateMyProfile(
            @RequestBody CreateOrUpdatePetsitterRequest request) {

        Long ownerId = getCurrentOwnerId();

        CreateOrUpdatePetsitterCommand command = new CreateOrUpdatePetsitterCommand(
                request.getHourlyRate(),
                request.getYearsExperience(),
                request.getBio(),
                request.getAvailableDate());

        PetsitterResponse response = petsitterCommandService.createOrUpdateForOwner(ownerId, command);
        return ResponseEntity.ok(response);
    }

    // get MY petsitter profile
    @GetMapping("/me")
    public ResponseEntity<PetsitterView> getMyPetsitterProfile() {
        Long ownerId = getCurrentOwnerId();
        PetsitterView view = petsitterQueryService.getByOwnerId(ownerId);
        return ResponseEntity.ok(view);
    }

    // list petsitters available on a given date
    @GetMapping("/date")
    public ResponseEntity<List<PetsitterView>> findAvailable(
            @RequestParam("date") String dateString) {

        LocalDate date = LocalDate.parse(dateString);
        List<PetsitterView> list = petsitterQueryService.findAvailableOn(date);
        return ResponseEntity.ok(list);
    }

    private Long getCurrentOwnerId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("No authenticated user");
        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getOwner().getId();
    }
}
