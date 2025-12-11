package be.petsitgroup.petsitting.review.application.command;

import be.petsitgroup.petsitting.dto.review.ReviewResponse;
import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.model.Playdate;
import be.petsitgroup.petsitting.model.Review;
import be.petsitgroup.petsitting.review.domain.ReviewCreatedEvent;
import be.petsitgroup.petsitting.repository.PlaydateRepository;
import be.petsitgroup.petsitting.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewCommandServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PlaydateRepository playdateRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ReviewCommandServiceImpl reviewCommandService;

    @Test
    void createReview_savesReview_andPublishesEvent() {
        // arrange
        Long playdateId = 10L;
        int rating = 5;
        String comment = "Great!";

        Petsitter petsitter = new Petsitter();
        petsitter.setId(3L);

        Playdate playdate = new Playdate();
        playdate.setId(playdateId);
        playdate.setPetsitter(petsitter);

        when(playdateRepository.findById(playdateId))
                .thenReturn(Optional.of(playdate));

        // simulate DB saved review
        Review savedReview = new Review();
        savedReview.setId(100L);
        savedReview.setPlaydate(playdate);
        savedReview.setRating(rating);
        savedReview.setComment(comment);
        savedReview.setDate(LocalDateTime.now());

        when(reviewRepository.save(any(Review.class)))
                .thenReturn(savedReview);

        CreateReviewCommand command = new CreateReviewCommand(
                playdateId,
                rating,
                comment);

        // act
        ReviewResponse response = reviewCommandService.createReview(command);

        // assert: repository.save called
        verify(reviewRepository, times(1)).save(any(Review.class));

        // assert: event published with correct data
        ArgumentCaptor<ReviewCreatedEvent> eventCaptor = ArgumentCaptor.forClass(ReviewCreatedEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        ReviewCreatedEvent event = eventCaptor.getValue();
        assertThat(event.getPlaydateId()).isEqualTo(playdateId);
        assertThat(event.getPetsitterId()).isEqualTo(petsitter.getId());
        assertThat(event.getRating()).isEqualTo(rating);

        // assert: response matches saved review
        assertThat(response.getId()).isEqualTo(savedReview.getId());
        assertThat(response.getRating()).isEqualTo(rating);
        assertThat(response.getComment()).isEqualTo(comment);
    }

    @Test
    void createReview_whenPlaydateNotFound_throwsException() {
        // arrange
        Long playdateId = 99L;
        when(playdateRepository.findById(playdateId))
                .thenReturn(Optional.empty());

        CreateReviewCommand command = new CreateReviewCommand(
                playdateId,
                4,
                "ok");

        // act + assert
        assertThatThrownBy(() -> reviewCommandService.createReview(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Playdate not found");

        verify(reviewRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}
