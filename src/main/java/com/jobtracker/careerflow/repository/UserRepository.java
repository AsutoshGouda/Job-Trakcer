package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findByFirstNameAndLastName(String firstname, String lastname);
    Optional<UserEntity> getUserByUserId(UUID userID);
    Optional<UserEntity> getUserByPhoneNo(String phone_no);
    Optional<UserEntity> getUserByEmail(String email);
}
