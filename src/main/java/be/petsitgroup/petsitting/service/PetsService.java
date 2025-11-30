package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.CreatePetsRequest;
import be.petsitgroup.petsitting.dto.PetsResponse;

import java.util.List;

public interface PetsService {

    PetsResponse createPetForCurrentOwner(CreatePetsRequest request);

    List<PetsResponse> getMyPets();

    PetsResponse updateAvailability(Long petId, boolean available);
}
