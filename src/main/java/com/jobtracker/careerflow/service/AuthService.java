package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.IncorrectPasswordException;
import com.jobtracker.careerflow.Exception_Handling.UserAlreadyExistsException;
import com.jobtracker.careerflow.Exception_Handling.UserNotFoundException;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.UserRepository;
import com.jobtracker.careerflow.requestDTO.LoginRequestDTO;
import com.jobtracker.careerflow.requestDTO.RegisterRequestDTO;
import com.jobtracker.careerflow.responseDTO.LoginResponseDTO;
import com.jobtracker.careerflow.responseDTO.UserResponseDTO;
import com.jobtracker.careerflow.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.OffsetDateTime;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService,
                       JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO){
        if(userRepository.existsByEmail(registerRequestDTO.email())){
            throw new UserAlreadyExistsException("Email is already registered: "+ registerRequestDTO.email());
        }

        if(userRepository.existsByPhoneNo(registerRequestDTO.phoneNo())){
            throw new UserAlreadyExistsException("Phone Number is already registered: "+ registerRequestDTO.phoneNo());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(registerRequestDTO.firstName());
        userEntity.setLastName(registerRequestDTO.lastName());
        userEntity.setEmail(registerRequestDTO.email());
        userEntity.setPhoneNo(registerRequestDTO.phoneNo());
        userEntity.setAddress(registerRequestDTO.address());
        String hash = passwordEncoder.encode(registerRequestDTO.password());
        userEntity.setPasswordHash(hash);

        UserEntity savedUser = userRepository.save(userEntity);
        return userService.mapToResponse(savedUser);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        UserEntity userEntity =
                userRepository.getUserByEmail(loginRequestDTO.email()).orElseThrow(()-> new UserNotFoundException(
                        "Incorrect UserName or Password"));


        if(!passwordEncoder.matches(loginRequestDTO.password(), userEntity.getPasswordHash())){
            throw new IncorrectPasswordException("Incorrect UserName or Password");
        }

        String token = jwtService.generateToken(userEntity.getEmail());

        String email = jwtService.extractEmail(token);

        System.out.println(email);

        return new LoginResponseDTO(token);
    }

}
