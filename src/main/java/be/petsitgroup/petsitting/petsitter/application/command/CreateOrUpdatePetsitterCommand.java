package be.petsitgroup.petsitting.petsitter.application.command;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateOrUpdatePetsitterCommand {

    private BigDecimal hourlyRate;
    private Integer yearsExperience;
    private String bio;
    private LocalDate availableDate;

    public CreateOrUpdatePetsitterCommand() {
    }

    public CreateOrUpdatePetsitterCommand(BigDecimal hourlyRate,
            Integer yearsExperience,
            String bio,
            LocalDate availableDate) {
        this.hourlyRate = hourlyRate;
        this.yearsExperience = yearsExperience;
        this.bio = bio;
        this.availableDate = availableDate;
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
