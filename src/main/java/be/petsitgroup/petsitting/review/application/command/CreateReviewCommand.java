package be.petsitgroup.petsitting.review.application.command;

public class CreateReviewCommand {

    private Long playdateId;
    private int rating;
    private String comment;

    public CreateReviewCommand() {
    }

    public CreateReviewCommand(Long playdateId, int rating, String comment) {
        this.playdateId = playdateId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getPlaydateId() {
        return playdateId;
    }

    public void setPlaydateId(Long playdateId) {
        this.playdateId = playdateId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
