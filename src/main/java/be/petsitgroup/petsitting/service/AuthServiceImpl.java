package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.auth.AuthResponse;
import be.petsitgroup.petsitting.dto.auth.LoginRequest;
import be.petsitgroup.petsitting.dto.auth.RegisterRequest;
import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.owner.application.command.OwnerCommandService;
import be.petsitgroup.petsitting.owner.application.command.RegisterOwnerCommand;
import be.petsitgroup.petsitting.repository.OwnerRepository;
import be.petsitgroup.petsitting.security.JwtTokenProvider;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final OwnerCommandService ownerCommandService;

    public AuthServiceImpl(OwnerRepository ownerRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            OwnerCommandService ownerCommandService) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.ownerCommandService = ownerCommandService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        // build command from incoming DTO
        RegisterOwnerCommand command = new RegisterOwnerCommand(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getAddress(),
                request.getPhoneNumber());

        // delegate to OwnerCommandService (CQRS - command side)
        Long ownerId = ownerCommandService.registerOwner(command);

        // load owner again to build JWT (or let registerOwner return Owner directly)
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found after registration"));

        String token = jwtTokenProvider.generateToken(owner);

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Owner owner = ownerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), owner.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(owner);
        return new AuthResponse(token);
    }
}
