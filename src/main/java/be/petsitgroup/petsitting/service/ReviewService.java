package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.CreateReviewRequest;
import be.petsitgroup.petsitting.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(CreateReviewRequest request);

    List<ReviewResponse> getReviewsForPetsitter(Long petsitterId);

    List<ReviewResponse> getMyReviews(); // reviews I (owner) wrote
}
