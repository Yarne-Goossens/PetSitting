package be.petsitgroup.petsitting.repository;

import be.petsitgroup.petsitting.model.Playdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaydateRepository extends JpaRepository<Playdate, Long> {

    // All playdates for a given petsitter
    List<Playdate> findByPetsitterId(Long petsitterId);

    // All playdates for a given pet
    List<Playdate> findByPetId(Long petId);

    // All playdates for a given owner (via pet.owner.id)
    List<Playdate> findByPetOwnerId(Long ownerId);
}
