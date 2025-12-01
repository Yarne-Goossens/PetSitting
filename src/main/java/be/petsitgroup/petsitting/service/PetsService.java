package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.pets.CreatePetsRequest;
import be.petsitgroup.petsitting.dto.pets.PetsResponse;

import java.util.List;

public interface PetsService {

    PetsResponse createPetForCurrentOwner(CreatePetsRequest request);

    List<PetsResponse> getMyPets();

    PetsResponse updateAvailability(Long petId, boolean available);
}
