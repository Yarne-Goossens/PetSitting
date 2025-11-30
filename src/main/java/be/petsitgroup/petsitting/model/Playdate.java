package be.petsitgroup.petsitting.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playdates")
public class Playdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petsitter_id")
    private Petsitter petsitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pets pet;

    private LocalDateTime date;

    private BigDecimal price;

    // e.g. REQUESTED, CONFIRMED, COMPLETED, CANCELLED
    private String status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "playdate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // ----------------------------------------------------
    // Constructors
    // ----------------------------------------------------

    public Playdate() {
    }

    public Playdate(Long id, String location, Petsitter petsitter, Pets pet,
            LocalDateTime date, BigDecimal price, String status,
            String notes, List<Review> reviews) {
        this.id = id;
        this.location = location;
        this.petsitter = petsitter;
        this.pet = pet;
        this.date = date;
        this.price = price;
        this.status = status;
        this.notes = notes;
        this.reviews = reviews;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Petsitter getPetsitter() {
        return petsitter;
    }

    public void setPetsitter(Petsitter petsitter) {
        this.petsitter = petsitter;
    }

    public Pets getPet() {
        return pet;
    }

    public void setPet(Pets pet) {
        this.pet = pet;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    // ----------------------------------------------------
    // Helper methods
    // ----------------------------------------------------

    public void addReview(Review review) {
        reviews.add(review);
        review.setPlaydate(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setPlaydate(null);
    }
}
