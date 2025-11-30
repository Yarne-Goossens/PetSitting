package be.petsitgroup.petsitting.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playdate_id")
    private Playdate playdate;

    // ----------------------------------------------------
    // Constructors
    // ----------------------------------------------------

    public Review() {
    }

    public Review(Long id, Integer rating, String comment,
            LocalDateTime date, Playdate playdate) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.playdate = playdate;
    }

    // ----------------------------------------------------
    // Getters & Setters
    // ----------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Playdate getPlaydate() {
        return playdate;
    }

    public void setPlaydate(Playdate playdate) {
        this.playdate = playdate;
    }
}
