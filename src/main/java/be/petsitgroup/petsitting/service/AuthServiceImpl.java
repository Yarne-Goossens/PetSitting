package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.AuthResponse;
import be.petsitgroup.petsitting.dto.LoginRequest;
import be.petsitgroup.petsitting.dto.RegisterRequest;
import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Role;
import be.petsitgroup.petsitting.repository.OwnerRepository;
import be.petsitgroup.petsitting.security.JwtTokenProvider;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(OwnerRepository ownerRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (ownerRepository.existsByEmail(request.getEmail())) {
            // basic error for now; later we add custom exception + handler
            throw new RuntimeException("Email already in use");
        }

        Owner owner = new Owner();
        owner.setName(request.getName());
        owner.setPhoneNumber(request.getPhoneNumber());
        owner.setEmail(request.getEmail());
        owner.setPassword(passwordEncoder.encode(request.getPassword()));
        owner.setAddress(request.getAddress());
        owner.setRole(Role.OWNER);

        Owner saved = ownerRepository.save(owner);

        String token = jwtTokenProvider.generateToken(saved);
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
