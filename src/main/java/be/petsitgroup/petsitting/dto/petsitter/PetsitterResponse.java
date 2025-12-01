package be.petsitgroup.petsitting.dto.petsitter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PetsitterResponse {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private BigDecimal hourlyRate;
    private Integer yearsExperience;
    private Double rating;
    private String bio;
    private LocalDate availableDate;

    public PetsitterResponse() {
    }

    public PetsitterResponse(Long id, String name, String phoneNumber, String email,
            BigDecimal hourlyRate, Integer yearsExperience,
            Double rating, String bio, LocalDate availableDate) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.hourlyRate = hourlyRate;
        this.yearsExperience = yearsExperience;
        this.rating = rating;
        this.bio = bio;
        this.availableDate = availableDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
}
