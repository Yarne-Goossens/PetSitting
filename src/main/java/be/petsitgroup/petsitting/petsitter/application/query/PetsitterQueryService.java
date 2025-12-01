package be.petsitgroup.petsitting.petsitter.application.query;

import java.time.LocalDate;
import java.util.List;

public interface PetsitterQueryService {

    PetsitterView getByOwnerId(Long ownerId);

    List<PetsitterView> findAvailableOn(LocalDate date);
}
