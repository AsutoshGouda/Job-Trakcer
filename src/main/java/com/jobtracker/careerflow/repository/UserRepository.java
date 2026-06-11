package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findByFirstNameAndLastName(String firstname, String lastname);

}
