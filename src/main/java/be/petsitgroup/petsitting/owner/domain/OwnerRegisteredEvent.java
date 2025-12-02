package be.petsitgroup.petsitting.owner.domain;

import java.time.LocalDateTime;

public class OwnerRegisteredEvent {

    private final Long ownerId;
    private final String email;
    private final String name;
    private final LocalDateTime registeredAt;

    public OwnerRegisteredEvent(Long ownerId, String email, String name, LocalDateTime registeredAt) {
        this.ownerId = ownerId;
        this.email = email;
        this.name = name;
        this.registeredAt = registeredAt;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
}
