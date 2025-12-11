package be.petsitgroup.petsitting.petsitter.domain;

import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.model.Review;
import be.petsitgroup.petsitting.review.domain.ReviewCreatedEvent;
import be.petsitgroup.petsitting.repository.PetsitterRepository;
import be.petsitgroup.petsitting.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetsitterRatingUpdaterTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PetsitterRepository petsitterRepository;

    @InjectMocks
    private PetsitterRatingUpdater ratingUpdater;

    @Test
    void onReviewCreated_recalculatesAverageRating() {
        // arrange
        Long petsitterId = 5L;

        Review r1 = new Review();
        r1.setRating(5);

        Review r2 = new Review();
        r2.setRating(3);

        when(reviewRepository.findByPlaydate_Petsitter_Id(petsitterId))
                .thenReturn(Arrays.asList(r1, r2));

        Petsitter petsitter = new Petsitter();
        petsitter.setId(petsitterId);
        petsitter.setRating(0.0);

        when(petsitterRepository.findById(petsitterId))
                .thenReturn(Optional.of(petsitter));

        ReviewCreatedEvent event = new ReviewCreatedEvent(
                100L,
                10L,
                petsitterId,
                4,
                LocalDateTime.now());

        // act
        ratingUpdater.onReviewCreated(event);

        // assert: petsitter was saved with new rating = (5+3)/2 = 4.0
        ArgumentCaptor<Petsitter> captor = ArgumentCaptor.forClass(Petsitter.class);
        verify(petsitterRepository).save(captor.capture());
        Petsitter saved = captor.getValue();

        assertThat(saved.getId()).isEqualTo(petsitterId);
        assertThat(saved.getRating()).isEqualTo(4.0);
    }
}
