package be.petsitgroup.petsitting.review.api;

import be.petsitgroup.petsitting.dto.review.CreateReviewRequest;
import be.petsitgroup.petsitting.dto.review.ReviewResponse;
import be.petsitgroup.petsitting.review.application.command.CreateReviewCommand;
import be.petsitgroup.petsitting.review.application.command.ReviewCommandService;
import be.petsitgroup.petsitting.review.application.query.ReviewQueryService;
import be.petsitgroup.petsitting.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    public ReviewController(ReviewCommandService reviewCommandService,
            ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    // CREATE review (command)
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody CreateReviewRequest request) {

        CreateReviewCommand command = new CreateReviewCommand(
                request.getPlaydateId(),
                request.getRating(),
                request.getComment());

        ReviewResponse response = reviewCommandService.createReview(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET all reviews for a given petsitter (query)
    @GetMapping("/petsitter/{petsitterId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsForPetsitter(
            @PathVariable Long petsitterId) {

        List<ReviewResponse> reviews = reviewQueryService.getReviewsForPetsitter(petsitterId);
        return ResponseEntity.ok(reviews);
    }

    // GET all reviews for "my" petsitter profile (query)
    @GetMapping("/me")
    public ResponseEntity<List<ReviewResponse>> getMyReviews() {

        Long ownerId = getCurrentOwnerId();
        List<ReviewResponse> reviews = reviewQueryService.getReviewsForOwnerPetsitter(ownerId);
        return ResponseEntity.ok(reviews);
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
