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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        log.info("Handling ReviewCreatedEvent for petsitterId={} reviewId={}",
                event.getPetsitterId(), event.getReviewId());

        // 1. Get all reviews for this petsitter
        var reviews = reviewRepository.findByPlaydate_Petsitter_Id(event.getPetsitterId());

        if (reviews.isEmpty()) {
            log.warn("No reviews found for petsitterId={} while handling ReviewCreatedEvent",
                    event.getPetsitterId());
            return;
        }

        double average = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        // 2. Load petsitter
        Petsitter petsitter = petsitterRepository.findById(event.getPetsitterId())
                .orElseThrow(() -> {
                    log.error("Petsitter with id={} not found while updating rating",
                            event.getPetsitterId());
                    return new RuntimeException("Petsitter not found");
                });

        double oldRating = petsitter.getRating() != null ? petsitter.getRating() : 0.0;
        petsitter.setRating(average);
        petsitterRepository.save(petsitter);

        log.info("Updated petsitterId={} rating from {} to {} based on {} reviews",
                petsitter.getId(), oldRating, average, reviews.size());
    }
}