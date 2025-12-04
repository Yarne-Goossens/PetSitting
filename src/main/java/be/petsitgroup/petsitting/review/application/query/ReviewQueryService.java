package be.petsitgroup.petsitting.review.application.query;

import be.petsitgroup.petsitting.dto.review.ReviewResponse;

import java.util.List;

public interface ReviewQueryService {

    // all reviews for a given petsitter
    List<ReviewResponse> getReviewsForPetsitter(Long petsitterId);

    // all reviews for the petsitter that belongs to a given owner
    List<ReviewResponse> getReviewsForOwnerPetsitter(Long ownerId);
}
