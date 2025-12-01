package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.CreateReviewRequest;
import be.petsitgroup.petsitting.dto.ReviewResponse;
import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.model.Playdate;
import be.petsitgroup.petsitting.model.Review;
import be.petsitgroup.petsitting.repository.PetsitterRepository;
import be.petsitgroup.petsitting.repository.PlaydateRepository;
import be.petsitgroup.petsitting.repository.ReviewRepository;
import be.petsitgroup.petsitting.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaydateRepository playdateRepository;
    private final PetsitterRepository petsitterRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
            PlaydateRepository playdateRepository,
            PetsitterRepository petsitterRepository) {
        this.reviewRepository = reviewRepository;
        this.playdateRepository = playdateRepository;
        this.petsitterRepository = petsitterRepository;
    }

    @Override
    public ReviewResponse createReview(CreateReviewRequest request) {
        Owner owner = getCurrentOwner();

        Playdate playdate = playdateRepository.findById(request.getPlaydateId())
                .orElseThrow(() -> new RuntimeException("Playdate not found"));

        // Only owner of the pet can review this playdate
        if (!playdate.getPet().getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You can only review your own playdates");
        }

        // Only allow reviews for completed playdates (optional rule)
        if (!"COMPLETED".equalsIgnoreCase(playdate.getStatus())) {
            throw new RuntimeException("You can only review completed playdates");
        }

        // Prevent multiple reviews for the same playdate
        if (reviewRepository.findByPlaydateId(playdate.getId()).isPresent()) {
            throw new RuntimeException("You already reviewed this playdate");
        }

        Review review = new Review();
        review.setPlaydate(playdate);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setDate(LocalDateTime.now());

        Review saved = reviewRepository.save(review);

        // update petsitter average rating
        updatePetsitterRating(playdate.getPetsitter());

        return toResponse(saved);
    }

    @Override
    public List<ReviewResponse> getReviewsForPetsitter(Long petsitterId) {
        List<Review> reviews = reviewRepository.findByPlaydatePetsitterId(petsitterId);
        return reviews.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getMyReviews() {
        Owner owner = getCurrentOwner();
        List<Review> reviews = reviewRepository.findByPlaydatePetOwnerId(owner.getId());
        return reviews.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private void updatePetsitterRating(Petsitter petsitter) {
        if (petsitter == null || petsitter.getId() == null) {
            return;
        }
        List<Review> reviews = reviewRepository.findByPlaydatePetsitterId(petsitter.getId());
        if (reviews.isEmpty()) {
            petsitter.setRating(0.0);
        } else {
            double avg = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
            petsitter.setRating(avg);
        }
        petsitterRepository.save(petsitter);
    }

    private ReviewResponse toResponse(Review r) {
        Playdate p = r.getPlaydate();
        Petsitter ps = p != null ? p.getPetsitter() : null;
        Owner petsitterOwner = ps != null ? ps.getOwner() : null;

        return new ReviewResponse(
                r.getId(),
                p != null ? p.getId() : null,
                ps != null ? ps.getId() : null,
                petsitterOwner != null ? petsitterOwner.getName() : null,
                r.getRating(),
                r.getComment(),
                r.getDate());
    }

    private Owner getCurrentOwner() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("No authenticated user");
        }
        return ((CustomUserDetails) auth.getPrincipal()).getOwner();
    }
}
