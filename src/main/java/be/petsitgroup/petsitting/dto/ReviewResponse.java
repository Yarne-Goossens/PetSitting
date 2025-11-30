package be.petsitgroup.petsitting.dto;

import java.time.LocalDateTime;

public class ReviewResponse {

    private Long id;
    private Long playdateId;
    private Long petsitterId;
    private String petsitterName;
    private Integer rating;
    private String comment;
    private LocalDateTime date;

    public ReviewResponse() {
    }

    public ReviewResponse(Long id,
            Long playdateId,
            Long petsitterId,
            String petsitterName,
            Integer rating,
            String comment,
            LocalDateTime date) {
        this.id = id;
        this.playdateId = playdateId;
        this.petsitterId = petsitterId;
        this.petsitterName = petsitterName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlaydateId() {
        return playdateId;
    }

    public void setPlaydateId(Long playdateId) {
        this.playdateId = playdateId;
    }

    public Long getPetsitterId() {
        return petsitterId;
    }

    public void setPetsitterId(Long petsitterId) {
        this.petsitterId = petsitterId;
    }

    public String getPetsitterName() {
        return petsitterName;
    }

    public void setPetsitterName(String petsitterName) {
        this.petsitterName = petsitterName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
