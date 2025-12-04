package be.petsitgroup.petsitting.repository;

import be.petsitgroup.petsitting.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // one review per playdate (from owner)
    Optional<Review> findByPlaydateId(Long playdateId);

    // all reviews for a given petsitter
    List<Review> findByPlaydatePetsitterId(Long petsitterId);

    // all reviews written by an owner (via playdate.pet.owner.id)
    List<Review> findByPlaydatePetOwnerId(Long ownerId);

    // all reviews for a petsitter via playdate.petsitter.id
    List<Review> findByPlaydate_Petsitter_Id(Long petsitterId);
}
