package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.responseDTO.UserResponseDTO;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserResponseDTO getUserById(UUID userID){
        UserEntity userEntity = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User Not Found"));
        return new UserResponseDTO(
                userEntity.getUserId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail()
        );
    }

    public UserResponseDTO getUsersByEmail(String email){ return userRepository.getUserByEmail(email); }
}
