package be.petsitgroup.petsitting.petsitter.application.command;

import be.petsitgroup.petsitting.dto.petsitter.PetsitterResponse;
import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.repository.OwnerRepository;
import be.petsitgroup.petsitting.repository.PetsitterRepository;
import org.springframework.stereotype.Service;

@Service
public class PetsitterCommandServiceImpl implements PetsitterCommandService {

    private final PetsitterRepository petsitterRepository;
    private final OwnerRepository ownerRepository;

    public PetsitterCommandServiceImpl(PetsitterRepository petsitterRepository,
            OwnerRepository ownerRepository) {
        this.petsitterRepository = petsitterRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public PetsitterResponse createOrUpdateForOwner(Long ownerId, CreateOrUpdatePetsitterCommand command) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // find existing petsitter profile for this owner
        Petsitter petsitter = petsitterRepository.findByOwnerId(ownerId)
                .orElseGet(() -> {
                    Petsitter p = new Petsitter();
                    p.setOwner(owner);
                    // if you still store name/email/phone on Petsitter, fill them from owner here
                    p.setName(owner.getName());
                    p.setEmail(owner.getEmail());
                    p.setPhoneNumber(owner.getPhoneNumber());
                    return p;
                });

        petsitter.setHourlyRate(command.getHourlyRate());
        petsitter.setYearsExperience(command.getYearsExperience());
        petsitter.setBio(command.getBio());
        petsitter.setAvailableDate(command.getAvailableDate());

        Petsitter saved = petsitterRepository.save(petsitter);

        return toResponse(saved);
    }

    private PetsitterResponse toResponse(Petsitter p) {
        PetsitterResponse resp = new PetsitterResponse();
        resp.setId(p.getId());
        resp.setName(p.getName());
        resp.setEmail(p.getEmail());
        resp.setPhoneNumber(p.getPhoneNumber());
        resp.setHourlyRate(p.getHourlyRate());
        resp.setYearsExperience(p.getYearsExperience());
        resp.setRating(p.getRating());
        resp.setBio(p.getBio());
        resp.setAvailableDate(p.getAvailableDate());
        return resp;
    }
}
