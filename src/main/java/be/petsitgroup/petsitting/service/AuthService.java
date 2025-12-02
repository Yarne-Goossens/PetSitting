package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.auth.AuthResponse;
import be.petsitgroup.petsitting.dto.auth.LoginRequest;
import be.petsitgroup.petsitting.dto.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}