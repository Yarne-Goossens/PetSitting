package be.petsitgroup.petsitting.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "petsitters")
public class Petsitter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private String name;

    // @Column(name = "phone_number")
    // private String phoneNumber;

    // private String email;

    @OneToOne
    @JoinColumn(name = "owner_id", unique = true)
    private Owner owner;

    @Column(name = "hourly_rate")
    private BigDecimal hourlyRate;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    // Overall rating (e.g. 4.5)
    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "available_date")
    private LocalDate availableDate;

    @OneToMany(mappedBy = "petsitter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playdate> playdates = new ArrayList<>();

    // ----------------------------------------------------
    // Constructors
    // ----------------------------------------------------

    public Petsitter() {
    }

    public Petsitter(Long id,
            // String name, String phoneNumber, String email,
            BigDecimal hourlyRate, Integer yearsExperience,
            Double rating, String bio, LocalDate availableDate,
            List<Playdate> playdates) {
        this.id = id;
        // this.name = name;
        // this.phoneNumber = phoneNumber;
        // this.email = email;
        this.hourlyRate = hourlyRate;
        this.yearsExperience = yearsExperience;
        this.rating = rating;
        this.bio = bio;
        this.availableDate = availableDate;
        this.playdates = playdates;
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

    // public String getName() {
    // return name;
    // }

    // public void setName(String name) {
    // this.name = name;
    // }

    // public String getPhoneNumber() {
    // return phoneNumber;
    // }

    // public void setPhoneNumber(String phoneNumber) {
    // this.phoneNumber = phoneNumber;
    // }

    // public String getEmail() {
    // return email;
    // }

    // public void setEmail(String email) {
    // this.email = email;
    // }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Playdate> getPlaydates() {
        return playdates;
    }

    public void setPlaydates(List<Playdate> playdates) {
        this.playdates = playdates;
    }

    // ----------------------------------------------------
    // Helper methods
    // ----------------------------------------------------

    public void addPlaydate(Playdate playdate) {
        playdates.add(playdate);
        playdate.setPetsitter(this);
    }

    public void removePlaydate(Playdate playdate) {
        playdates.remove(playdate);
        playdate.setPetsitter(null);
    }
}
