package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.LoginRequestDTO;
import com.jobtracker.careerflow.requestDTO.RegisterRequestDTO;
import com.jobtracker.careerflow.responseDTO.LoginResponseDTO;
import com.jobtracker.careerflow.responseDTO.UserResponseDTO;
import com.jobtracker.careerflow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserResponseDTO register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO){
        return authService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return authService.login(loginRequestDTO);
    }
}
