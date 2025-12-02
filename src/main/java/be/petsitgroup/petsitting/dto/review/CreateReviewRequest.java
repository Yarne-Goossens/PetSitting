package be.petsitgroup.petsitting.dto.review;

public class CreateReviewRequest {

    private Long playdateId;
    private Integer rating; // 1-5
    private String comment;

    public CreateReviewRequest() {
    }

    public Long getPlaydateId() {
        return playdateId;
    }

    public void setPlaydateId(Long playdateId) {
        this.playdateId = playdateId;
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
}
