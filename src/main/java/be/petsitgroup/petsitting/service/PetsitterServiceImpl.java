package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.CreateOrUpdatePetsitterRequest;
import be.petsitgroup.petsitting.dto.PetsitterResponse;
import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.model.Role;
import be.petsitgroup.petsitting.repository.OwnerRepository;
import be.petsitgroup.petsitting.repository.PetsitterRepository;
import be.petsitgroup.petsitting.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetsitterServiceImpl implements PetsitterService {

    private final PetsitterRepository petsitterRepository;
    private final OwnerRepository ownerRepository;

    public PetsitterServiceImpl(PetsitterRepository petsitterRepository,
            OwnerRepository ownerRepository) {
        this.petsitterRepository = petsitterRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public PetsitterResponse createOrUpdateMyProfile(CreateOrUpdatePetsitterRequest request) {
        Owner owner = getCurrentOwner();

        // find existing petsitter profile or create new one
        Petsitter petsitter = petsitterRepository.findById(owner.getId())
                .orElseGet(Petsitter::new);

        petsitter.setOwner(owner);
        petsitter.setHourlyRate(request.getHourlyRate());
        petsitter.setYearsExperience(request.getYearsExperience());
        petsitter.setBio(request.getBio());
        petsitter.setAvailableDate(request.getAvailableDate());

        if (petsitter.getRating() == null) {
            petsitter.setRating(0.0); // start at 0, will be updated with reviews
        }

        // update owner contact info from request
        owner.setName(request.getName());
        owner.setPhoneNumber(request.getPhoneNumber());
        owner.setEmail(request.getEmail());

        // make sure owner has PETSITTER role
        if (owner.getRole() != Role.PETSITTER) {
            owner.setRole(Role.PETSITTER);
        }

        ownerRepository.save(owner);
        Petsitter saved = petsitterRepository.save(petsitter);

        return toResponse(saved);
    }

    @Override
    public PetsitterResponse getMyProfile() {
        Owner owner = getCurrentOwner();

        Petsitter petsitter = petsitterRepository.findById(owner.getId())
                .orElseThrow(() -> new RuntimeException("You are not registered as a petsitter yet"));

        return toResponse(petsitter);
    }

    @Override
    public List<PetsitterResponse> getAllPetsitters() {
        List<Petsitter> all = petsitterRepository.findAll();
        return all.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PetsitterResponse toResponse(Petsitter p) {
        Owner owner = p.getOwner();

        return new PetsitterResponse(
                p.getId(),
                owner != null ? owner.getName() : null,
                owner != null ? owner.getPhoneNumber() : null,
                owner != null ? owner.getEmail() : null,
                p.getHourlyRate(),
                p.getYearsExperience(),
                p.getRating(),
                p.getBio(),
                p.getAvailableDate());
    }

    private Owner getCurrentOwner() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("No authenticated user");
        }
        return ((CustomUserDetails) auth.getPrincipal()).getOwner();
    }
}
