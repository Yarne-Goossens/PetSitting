package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.AuthResponse;
import be.petsitgroup.petsitting.dto.LoginRequest;
import be.petsitgroup.petsitting.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}