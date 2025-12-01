package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.review.CreateReviewRequest;
import be.petsitgroup.petsitting.dto.review.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(CreateReviewRequest request);

    List<ReviewResponse> getReviewsForPetsitter(Long petsitterId);

    List<ReviewResponse> getMyReviews(); // reviews I (owner) wrote
}
