package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.pets.CreatePetsRequest;
import be.petsitgroup.petsitting.dto.pets.PetsResponse;
import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Pets;
import be.petsitgroup.petsitting.repository.OwnerRepository;
import be.petsitgroup.petsitting.repository.PetsRepository;
import be.petsitgroup.petsitting.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetsService {

    private final PetsRepository petsRepository;
    private final OwnerRepository ownerRepository;

    public PetServiceImpl(PetsRepository petsRepository, OwnerRepository ownerRepository) {
        this.petsRepository = petsRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public PetsResponse createPetForCurrentOwner(CreatePetsRequest request) {
        Owner owner = getCurrentOwner();

        Pets pets = new Pets();
        pets.setName(request.getName());
        pets.setType(request.getType());
        pets.setAge(request.getAge());
        pets.setOwner(owner);
        pets.setAvailableForPlaydate(false);

        Pets saved = petsRepository.save(pets);
        return toResponse(saved);
    }

    @Override
    public List<PetsResponse> getMyPets() {
        Owner owner = getCurrentOwner();
        List<Pets> pets = petsRepository.findByOwnerId(owner.getId());
        return pets.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public PetsResponse updateAvailability(Long petsId, boolean available) {
        Owner owner = getCurrentOwner();

        Pets pets = petsRepository.findById(petsId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        if (!pets.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You cannot modify someone else's pet");
        }

        pets.setAvailableForPlaydate(available);
        Pets saved = petsRepository.save(pets);
        return toResponse(saved);
    }

    private PetsResponse toResponse(Pets pets) {
        return new PetsResponse(
                pets.getId(),
                pets.getName(),
                pets.getType(),
                pets.getAge(),
                pets.isAvailableForPlaydate());
    }

    private Owner getCurrentOwner() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("No authenticated user");
        }
        CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
        return cud.getOwner();
    }
}
