package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.CreateOrUpdatePetsitterRequest;
import be.petsitgroup.petsitting.dto.PetsitterResponse;

import java.util.List;

public interface PetsitterService {

    PetsitterResponse createOrUpdateMyProfile(CreateOrUpdatePetsitterRequest request);

    PetsitterResponse getMyProfile();

    List<PetsitterResponse> getAllPetsitters();
}
