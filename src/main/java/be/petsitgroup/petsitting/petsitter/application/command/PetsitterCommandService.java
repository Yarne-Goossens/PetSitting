package be.petsitgroup.petsitting.petsitter.application.command;

import be.petsitgroup.petsitting.dto.petsitter.PetsitterResponse;

public interface PetsitterCommandService {

    PetsitterResponse createOrUpdateForOwner(Long ownerId, CreateOrUpdatePetsitterCommand command);
}
