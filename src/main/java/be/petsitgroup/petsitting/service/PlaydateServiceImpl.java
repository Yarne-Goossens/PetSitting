package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.CreatePlaydateRequest;
import be.petsitgroup.petsitting.dto.PlaydateResponse;
import be.petsitgroup.petsitting.dto.UpdatePlaydateStatusRequest;
import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Pets;
import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.model.Playdate;
import be.petsitgroup.petsitting.repository.PetsRepository;
import be.petsitgroup.petsitting.repository.PetsitterRepository;
import be.petsitgroup.petsitting.repository.PlaydateRepository;
import be.petsitgroup.petsitting.security.CustomUserDetails;
import be.petsitgroup.petsitting.service.PlaydateService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaydateServiceImpl implements PlaydateService {

    private final PlaydateRepository playdateRepository;
    private final PetsRepository petsRepository;
    private final PetsitterRepository petsitterRepository;

    public PlaydateServiceImpl(PlaydateRepository playdateRepository,
            PetsRepository petsRepository,
            PetsitterRepository petsitterRepository) {
        this.playdateRepository = playdateRepository;
        this.petsRepository = petsRepository;
        this.petsitterRepository = petsitterRepository;
    }

    @Override
    public PlaydateResponse createPlaydate(CreatePlaydateRequest request) {
        Owner owner = getCurrentOwner();

        Pets pets = petsRepository.findById(request.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        if (!pets.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You can only create playdates for your own pets");
        }

        Petsitter petsitter = petsitterRepository.findById(request.getPetsitterId())
                .orElseThrow(() -> new RuntimeException("Petsitter not found"));

        Playdate playdate = new Playdate();
        playdate.setPet(pets);
        playdate.setPetsitter(petsitter);
        playdate.setLocation(request.getLocation());
        playdate.setDate(request.getDate());
        playdate.setPrice(request.getPrice());
        playdate.setStatus("REQUESTED");
        playdate.setNotes(request.getNotes());

        Playdate saved = playdateRepository.save(playdate);
        return toResponse(saved);
    }

    @Override
    public List<PlaydateResponse> getMyPlaydatesAsOwner() {
        Owner owner = getCurrentOwner();
        List<Playdate> playdates = playdateRepository.findByPetOwnerId(owner.getId());
        return playdates.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlaydateResponse> getMyPlaydatesAsPetsitter() {
        Owner owner = getCurrentOwner();

        Petsitter petsitter = petsitterRepository.findById(owner.getId())
                .orElseThrow(() -> new RuntimeException("You are not registered as a petsitter"));

        List<Playdate> playdates = playdateRepository.findByPetsitterId(petsitter.getId());
        return playdates.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PlaydateResponse updateStatus(Long playdateId, UpdatePlaydateStatusRequest request) {
        Owner owner = getCurrentOwner();

        Playdate playdate = playdateRepository.findById(playdateId)
                .orElseThrow(() -> new RuntimeException("Playdate not found"));

        // Only the petsitter of this playdate can change its status
        Petsitter petsitter = playdate.getPetsitter();
        if (petsitter == null || petsitter.getOwner() == null
                || !petsitter.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You are not allowed to update this playdate");
        }

        playdate.setStatus(request.getStatus());
        Playdate saved = playdateRepository.save(playdate);
        return toResponse(saved);
    }

    private PlaydateResponse toResponse(Playdate p) {
        Pets pets = p.getPet();
        Petsitter petsitter = p.getPetsitter();
        Owner petsitterOwner = petsitter != null ? petsitter.getOwner() : null;

        return new PlaydateResponse(
                p.getId(),
                pets != null ? pets.getId() : null,
                pets != null ? pets.getName() : null,
                petsitter != null ? petsitter.getId() : null,
                petsitterOwner != null ? petsitterOwner.getName() : null,
                p.getDate(),
                p.getLocation(),
                p.getPrice(),
                p.getStatus(),
                p.getNotes());
    }

    private Owner getCurrentOwner() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("No authenticated user");
        }
        return ((CustomUserDetails) auth.getPrincipal()).getOwner();
    }
}
