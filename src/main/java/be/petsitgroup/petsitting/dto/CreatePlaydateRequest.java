package be.petsitgroup.petsitting.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreatePlaydateRequest {

    private Long petId;
    private Long petsitterId;
    private LocalDateTime date;
    private String location;
    private BigDecimal price;
    private String notes;

    public CreatePlaydateRequest() {
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getPetsitterId() {
        return petsitterId;
    }

    public void setPetsitterId(Long petsitterId) {
        this.petsitterId = petsitterId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
