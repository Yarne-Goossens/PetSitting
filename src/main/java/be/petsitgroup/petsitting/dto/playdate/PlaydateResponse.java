package be.petsitgroup.petsitting.dto.playdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PlaydateResponse {

    private Long id;
    private Long petId;
    private String petName;
    private Long petsitterId;
    private String petsitterName;
    private LocalDateTime date;
    private String location;
    private BigDecimal price;
    private String status;
    private String notes;

    public PlaydateResponse() {
    }

    public PlaydateResponse(Long id,
            Long petId,
            String petName,
            Long petsitterId,
            String petsitterName,
            LocalDateTime date,
            String location,
            BigDecimal price,
            String status,
            String notes) {
        this.id = id;
        this.petId = petId;
        this.petName = petName;
        this.petsitterId = petsitterId;
        this.petsitterName = petsitterName;
        this.date = date;
        this.location = location;
        this.price = price;
        this.status = status;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
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
}
