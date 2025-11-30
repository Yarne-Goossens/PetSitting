package be.petsitgroup.petsitting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.petsitgroup.petsitting.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    // For login / registration
    Optional<Owner> findByEmail(String email);

    boolean existsByEmail(String email);
}
