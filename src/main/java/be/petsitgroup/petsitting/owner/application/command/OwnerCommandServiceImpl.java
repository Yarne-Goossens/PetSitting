package be.petsitgroup.petsitting.owner.application.command;

import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Role;
import be.petsitgroup.petsitting.owner.domain.OwnerRegisteredEvent;
import be.petsitgroup.petsitting.repository.OwnerRepository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OwnerCommandServiceImpl implements OwnerCommandService {

    private static final Logger log = LoggerFactory.getLogger(OwnerCommandServiceImpl.class);

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

        log.info("Registering new owner with email={}", command.getEmail());

        // email unique check
        if (ownerRepository.existsByEmail(command.getEmail())) {
            log.warn("Registration failed: email {} already in use", command.getEmail());
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

        log.info("Successfully registered owner id={} email={}", saved.getId(), saved.getEmail());

        OwnerRegisteredEvent event = new OwnerRegisteredEvent(
                saved.getId(),
                saved.getEmail(),
                saved.getName(),
                LocalDateTime.now());
        eventPublisher.publishEvent(event);

        log.debug("Published OwnerRegisteredEvent for ownerId={}", saved.getId());

        return saved.getId();
    }

    @Override
    public void updateProfile(UpdateOwnerProfileCommand command) {
        log.info("Updating profile for ownerId={}", command.getOwnerId());

        Owner owner = ownerRepository.findById(command.getOwnerId())
                .orElseThrow(() -> {
                    log.error("Owner with id={} not found while updating profile", command.getOwnerId());
                    return new RuntimeException("Owner not found");
                });

        owner.setName(command.getName());
        owner.setAddress(command.getAddress());
        owner.setPhoneNumber(command.getPhoneNumber());

        ownerRepository.save(owner);

        log.info("Updated profile for ownerId={}", owner.getId());
    }
}