package be.petsitgroup.petsitting.controller;

import be.petsitgroup.petsitting.dto.review.CreateReviewRequest;
import be.petsitgroup.petsitting.dto.review.ReviewResponse;
import be.petsitgroup.petsitting.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // owner creates a review for a completed playdate
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody CreateReviewRequest request) {
        return ResponseEntity.ok(reviewService.createReview(request));
    }

    // all reviews for a given petsitter
    @GetMapping("/petsitter/{petsitterId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsForPetsitter(
            @PathVariable Long petsitterId) {
        return ResponseEntity.ok(reviewService.getReviewsForPetsitter(petsitterId));
    }

    // reviews written by the current owner
    @GetMapping("/me")
    public ResponseEntity<List<ReviewResponse>> getMyReviews() {
        return ResponseEntity.ok(reviewService.getMyReviews());
    }
}
