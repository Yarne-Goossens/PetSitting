package be.petsitgroup.petsitting.owner.application.command;

import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Role;
import be.petsitgroup.petsitting.owner.domain.OwnerRegisteredEvent;
import be.petsitgroup.petsitting.repository.OwnerRepository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OwnerCommandServiceImpl implements OwnerCommandService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public OwnerCommandServiceImpl(OwnerRepository ownerRepository,
            PasswordEncoder passwordEncoder,
            ApplicationEventPublisher eventPublisher) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Long registerOwner(RegisterOwnerCommand command) {

        // email unique check
        if (ownerRepository.existsByEmail(command.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Owner owner = new Owner();
        owner.setName(command.getName());
        owner.setEmail(command.getEmail());
        owner.setAddress(command.getAddress());
        owner.setPhoneNumber(command.getPhoneNumber());
        owner.setRole(Role.OWNER);
        owner.setPassword(passwordEncoder.encode(command.getPassword()));

        Owner saved = ownerRepository.save(owner);

        // publish domain event
        OwnerRegisteredEvent event = new OwnerRegisteredEvent(
                saved.getId(),
                saved.getEmail(),
                saved.getName(),
                LocalDateTime.now());
        eventPublisher.publishEvent(event);

        return saved.getId();
    }

    @Override
    public void updateProfile(UpdateOwnerProfileCommand command) {
        Owner owner = ownerRepository.findById(command.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        owner.setName(command.getName());
        owner.setAddress(command.getAddress());
        owner.setPhoneNumber(command.getPhoneNumber());

        ownerRepository.save(owner);

        // here you could publish OwnerProfileUpdatedEvent if you want
    }
}
