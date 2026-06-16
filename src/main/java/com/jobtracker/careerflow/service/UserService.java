package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.responseDTO.UserResponseDTO;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.UserRepository;
import com.jobtracker.careerflow.resquestDTO.CreateUserRequestDTO;
import com.jobtracker.careerflow.resquestDTO.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDTO mapToResponse(UserEntity userEntity){
        return mapToResponse(userEntity);
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserResponseDTO getUserById(UUID userID){
        UserEntity userEntity = userRepository.getUserByUserId(userID).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        return mapToResponse(userEntity);
    }

    public UserResponseDTO getUsersByEmail(String email){
        UserEntity userEntity = userRepository.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("Provided email is not registered."));
        return mapToResponse(userEntity);
    }

    public UserResponseDTO getUserByPhoneNo(long phoneNo){
        UserEntity userEntity = userRepository.getUserByPhoneNo(phoneNo).orElseThrow(() -> new UserNotFoundException("Provided phone number is not registered!"));
        return mapToResponse(userEntity);
    }

    public UserResponseDTO save(CreateUserRequestDTO userRequestDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userRequestDTO.firstName());
        userEntity.setLastName(userRequestDTO.lastName());
        userEntity.setEmail(userRequestDTO.email());
        userEntity.setAddress(userRequestDTO.address());
        userEntity.setPhoneNo(userRequestDTO.phoneNo());

        UserEntity savedUser = userRepository.save(userEntity);
        return mapToResponse(savedUser);
    }

    public UserResponseDTO updateUser_email(String email, UserRequestDTO updates){
        UserEntity userEntity = userRepository.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("Provided email is not registered."));
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
        if(updates.phoneNo() != 0){
            userEntity.setPhoneNo(updates.phoneNo());
        }
        UserEntity updatedUser = userRepository.save(userEntity);
        return mapToResponse(updatedUser);
    }

    public UserResponseDTO updateUser_phoneno(long phoneno, UserRequestDTO updates){
        UserEntity userEntity = userRepository.getUserByPhoneNo(phoneno).orElseThrow(() -> new UserNotFoundException("Provided Phone No. is not found."));
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
        if(updates.phoneNo() != 0){
            userEntity.setPhoneNo(updates.phoneNo());
        }
        UserEntity updatedUser = userRepository.save(userEntity);
        return mapToResponse(updatedUser);
    }

    public UserResponseDTO deleteUser_phone(long phoneno){
        UserEntity userEntity = userRepository.getUserByPhoneNo(phoneno).orElseThrow(() -> new UserNotFoundException("Phone No. is not registered"));
        userRepository.delete(userEntity);
        return mapToResponse(userEntity);
    }

    public UserResponseDTO deleteUser_email(String email){
        UserEntity userEntity = userRepository.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("Phone No. is not registered"));
        userRepository.delete(userEntity);
        return mapToResponse(userEntity);
    }
}
