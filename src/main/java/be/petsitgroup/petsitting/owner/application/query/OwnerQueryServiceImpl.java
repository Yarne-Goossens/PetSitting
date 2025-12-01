package be.petsitgroup.petsitting.owner.application.query;

import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.repository.OwnerRepository;

import org.springframework.stereotype.Service;

@Service
public class OwnerQueryServiceImpl implements OwnerQueryService {

    private final OwnerRepository ownerRepository;

    public OwnerQueryServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public OwnerProfileView getById(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return new OwnerProfileView(
                owner.getId(),
                owner.getName(),
                owner.getEmail(),
                owner.getAddress(),
                owner.getPhoneNumber(),
                owner.getRole() != null ? owner.getRole().name() : null);
    }
}
