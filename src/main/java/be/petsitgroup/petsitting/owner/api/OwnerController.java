package be.petsitgroup.petsitting.owner.api;

import be.petsitgroup.petsitting.owner.application.command.OwnerCommandService;
import be.petsitgroup.petsitting.owner.application.command.UpdateOwnerProfileCommand;
import be.petsitgroup.petsitting.owner.application.query.OwnerProfileView;
import be.petsitgroup.petsitting.owner.application.query.OwnerQueryService;
import be.petsitgroup.petsitting.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
@SecurityRequirement(name = "bearerAuth")
public class OwnerController {

    private final OwnerCommandService ownerCommandService;
    private final OwnerQueryService ownerQueryService;

    public OwnerController(OwnerCommandService ownerCommandService,
            OwnerQueryService ownerQueryService) {
        this.ownerCommandService = ownerCommandService;
        this.ownerQueryService = ownerQueryService;
    }

    @GetMapping("/me")
    public ResponseEntity<OwnerProfileView> getMyProfile() {
        Long ownerId = getCurrentOwnerId();
        OwnerProfileView view = ownerQueryService.getById(ownerId);
        return ResponseEntity.ok(view);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMyProfile(@RequestBody UpdateOwnerProfileRequest request) {
        Long ownerId = getCurrentOwnerId();

        UpdateOwnerProfileCommand command = new UpdateOwnerProfileCommand(
                ownerId,
                request.getName(),
                request.getAddress(),
                request.getPhoneNumber());

        ownerCommandService.updateProfile(command);
        return ResponseEntity.noContent().build();
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
