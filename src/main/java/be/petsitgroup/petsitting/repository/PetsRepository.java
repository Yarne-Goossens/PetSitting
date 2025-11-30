package be.petsitgroup.petsitting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import be.petsitgroup.petsitting.model.Pets;

@Repository
public interface PetsRepository extends JpaRepository<Pets, Long> {

    List<Pets> findByOwnerId(Long ownerId);
}