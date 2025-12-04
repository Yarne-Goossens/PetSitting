package be.petsitgroup.petsitting.review.application.command;

import be.petsitgroup.petsitting.dto.review.ReviewResponse;

public interface ReviewCommandService {

    ReviewResponse createReview(CreateReviewCommand command);
}
