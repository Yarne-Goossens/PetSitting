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

@Service
public class ReviewCommandServiceImpl implements ReviewCommandService {

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

        Playdate playdate = playdateRepository.findById(command.getPlaydateId())
                .orElseThrow(() -> new RuntimeException("Playdate not found"));

        Review review = new Review();
        review.setRating(command.getRating());
        review.setComment(command.getComment());
        review.setDate(LocalDateTime.now());
        review.setPlaydate(playdate);

        Review saved = reviewRepository.save(review);

        Long petsitterId = playdate.getPetsitter().getId();

        // publish domain event
        ReviewCreatedEvent event = new ReviewCreatedEvent(
                saved.getId(),
                playdate.getId(),
                petsitterId,
                saved.getRating(),
                saved.getDate());
        eventPublisher.publishEvent(event);

        // map to existing DTO
        ReviewResponse response = new ReviewResponse();
        response.setId(saved.getId());
        response.setRating(saved.getRating());
        response.setComment(saved.getComment());
        response.setDate(saved.getDate());
        return response;
    }
}
