package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.ApplicationEntity;
import com.jobtracker.careerflow.entity.InterviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InterviewRepository extends JpaRepository<InterviewEntity, UUID> {

    List<InterviewEntity> findByApplicationEntity_ApplicationIdAndApplicationEntity_UserEntity_UserId(UUID id, UUID userId);
    boolean existsByApplicationEntity(ApplicationEntity applicationEntity);
    boolean existsByApplicationEntityAndRoundNo(
            ApplicationEntity applicationEntity,
            int roundNo
    );

}
