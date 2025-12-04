package be.petsitgroup.petsitting.review.domain;

import java.time.LocalDateTime;

public class ReviewCreatedEvent {

    private final Long reviewId;
    private final Long playdateId;
    private final Long petsitterId;
    private final int rating;
    private final LocalDateTime createdAt;

    public ReviewCreatedEvent(Long reviewId,
            Long playdateId,
            Long petsitterId,
            int rating,
            LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.playdateId = playdateId;
        this.petsitterId = petsitterId;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getPlaydateId() {
        return playdateId;
    }

    public Long getPetsitterId() {
        return petsitterId;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
