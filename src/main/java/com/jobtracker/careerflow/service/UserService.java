package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.responseDTO.UserResponseDTO;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.UserRepository;
import com.jobtracker.careerflow.requestDTO.UserRequestDTO;
import org.springframework.stereotype.Service;

import com.jobtracker.careerflow.Exception_Handling.UserNotFoundException;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDTO mapToResponse(UserEntity userEntity){
        return new UserResponseDTO(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getPhoneNo(),
                userEntity.getEmail(),
                userEntity.getAddress()
        );
    }

    public UserResponseDTO getUser(UUID userId){
        UserEntity userEntity = userRepository.getUserByUserId(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        return mapToResponse(userEntity);
    }

    public UserResponseDTO updateUser(UUID userId, UserRequestDTO updates){
        UserEntity userEntity = userRepository.getUserByUserId(userId).orElseThrow(() -> new UserNotFoundException("User Not Found."));
        if(updates.firstName() != null){
            userEntity.setFirstName(updates.firstName());
        }
        if(updates.lastName() != null){
            userEntity.setLastName(updates.lastName());
        }
        if(updates.email() != null){
            userEntity.setEmail(updates.email());
        }
        if(updates.address() != null){
            userEntity.setAddress(updates.address());
        }
        if(updates.phoneNo() != null){
            userEntity.setPhoneNo(updates.phoneNo());
        }
        UserEntity updatedUser = userRepository.save(userEntity);
        return mapToResponse(updatedUser);
    }

    public UserResponseDTO deleteMe(UUID userId){
        UserEntity userEntity = userRepository.getUserByUserId(userId).orElseThrow(() -> new UserNotFoundException("User Not Found."));
        userRepository.delete(userEntity);
        return mapToResponse(userEntity);
    }
}
