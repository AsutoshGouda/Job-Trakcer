package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.responseDTO.UserResponseDTO;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findByFirstNameAndLastName(String firstname, String lastname);
    UserResponseDTO getUserByUserId(UUID userID);
    UserResponseDTO getUserByPhoneNo(long phone_no);
    UserResponseDTO getUserByEmail(String email);
}
