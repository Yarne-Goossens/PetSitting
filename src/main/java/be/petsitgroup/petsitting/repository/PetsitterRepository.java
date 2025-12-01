package be.petsitgroup.petsitting.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import be.petsitgroup.petsitting.model.Petsitter;

@Repository
public interface PetsitterRepository extends JpaRepository<Petsitter, Long> {

    List<Petsitter> findByRatingGreaterThanEqual(Double rating);

    Optional<Petsitter> findByOwnerId(Long ownerId);

    List<Petsitter> findByAvailableDate(LocalDate availableDate);

}
