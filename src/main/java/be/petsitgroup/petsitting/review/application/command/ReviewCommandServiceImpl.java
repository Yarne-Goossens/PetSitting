package be.petsitgroup.petsitting.review.application.command;

import be.petsitgroup.petsitting.dto.review.ReviewResponse;
import be.petsitgroup.petsitting.model.Playdate;
import be.petsitgroup.petsitting.model.Review;
import be.petsitgroup.petsitting.petsitter.application.query.PetsitterView;
import be.petsitgroup.petsitting.repository.PlaydateRepository;
import be.petsitgroup.petsitting.repository.ReviewRepository;
import be.petsitgroup.petsitting.review.domain.ReviewCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private static final Logger log = LoggerFactory.getLogger(ReviewCommandServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final PlaydateRepository playdateRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ReviewCommandServiceImpl(ReviewRepository reviewRepository,
            PlaydateRepository playdateRepository,
            ApplicationEventPublisher eventPublisher) {
        this.reviewRepository = reviewRepository;
        this.playdateRepository = playdateRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ReviewResponse createReview(CreateReviewCommand command) {

        log.info("Creating review for playdateId={} with rating={}",
                command.getPlaydateId(), command.getRating());

        Playdate playdate = playdateRepository.findById(command.getPlaydateId())
                .orElseThrow(() -> {
                    log.warn("Cannot create review: playdate {} not found", command.getPlaydateId());
                    return new RuntimeException("Playdate not found");
                });

        Review review = new Review();
        review.setPlaydate(playdate);
        review.setRating(command.getRating());
        review.setComment(command.getComment());
        review.setDate(LocalDateTime.now());

        Review saved = reviewRepository.save(review);

        log.info("Review created with id={} for playdateId={}",
                saved.getId(), playdate.getId());

        ReviewCreatedEvent event = new ReviewCreatedEvent(
                saved.getId(),
                playdate.getId(),
                playdate.getPetsitter().getId(),
                saved.getRating(),
                saved.getDate());
        eventPublisher.publishEvent(event);

        log.debug("Published ReviewCreatedEvent for reviewId={} petsitterId={}",
                saved.getId(), playdate.getPetsitter().getId());

        ReviewResponse response = new ReviewResponse();
        response.setId(saved.getId());
        response.setRating(saved.getRating());
        response.setComment(saved.getComment());
        response.setDate(saved.getDate());

        return response;
    }
}