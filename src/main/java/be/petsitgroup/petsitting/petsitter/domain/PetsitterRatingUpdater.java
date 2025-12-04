package be.petsitgroup.petsitting.petsitter.domain;

import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.model.Review;
import be.petsitgroup.petsitting.repository.PetsitterRepository;
import be.petsitgroup.petsitting.repository.ReviewRepository;
import be.petsitgroup.petsitting.review.domain.ReviewCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetsitterRatingUpdater {

    private static final Logger log = LoggerFactory.getLogger(PetsitterRatingUpdater.class);

    private final ReviewRepository reviewRepository;
    private final PetsitterRepository petsitterRepository;

    public PetsitterRatingUpdater(ReviewRepository reviewRepository,
            PetsitterRepository petsitterRepository) {
        this.reviewRepository = reviewRepository;
        this.petsitterRepository = petsitterRepository;
    }

    @EventListener
    public void onReviewCreated(ReviewCreatedEvent event) {

        Long petsitterId = event.getPetsitterId();

        // load all reviews for this petsitter
        List<Review> reviews = reviewRepository.findByPlaydate_Petsitter_Id(petsitterId);

        double avgRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        Petsitter petsitter = petsitterRepository.findById(petsitterId)
                .orElseThrow(() -> new RuntimeException("Petsitter not found"));

        petsitter.setRating(avgRating);
        petsitterRepository.save(petsitter);

        log.info("Updated petsitter {} rating to {}", petsitterId, avgRating);
    }
}
