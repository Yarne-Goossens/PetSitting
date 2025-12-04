package be.petsitgroup.petsitting.review.application.query;

import be.petsitgroup.petsitting.dto.review.ReviewResponse;
import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.model.Review;
import be.petsitgroup.petsitting.repository.PetsitterRepository;
import be.petsitgroup.petsitting.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final PetsitterRepository petsitterRepository;

    public ReviewQueryServiceImpl(ReviewRepository reviewRepository,
            PetsitterRepository petsitterRepository) {
        this.reviewRepository = reviewRepository;
        this.petsitterRepository = petsitterRepository;
    }

    @Override
    public List<ReviewResponse> getReviewsForPetsitter(Long petsitterId) {
        List<Review> reviews = reviewRepository.findByPlaydate_Petsitter_Id(petsitterId);
        return reviews.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getReviewsForOwnerPetsitter(Long ownerId) {
        Petsitter petsitter = petsitterRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Petsitter profile not found for owner"));

        List<Review> reviews = reviewRepository.findByPlaydate_Petsitter_Id(petsitter.getId());
        return reviews.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ReviewResponse toResponse(Review review) {
        ReviewResponse dto = new ReviewResponse();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setDate(review.getDate());
        return dto;
    }
}
