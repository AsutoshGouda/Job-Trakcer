package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.ApplicationEntity;
import com.jobtracker.careerflow.entity.InterviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InterviewRepository extends JpaRepository<InterviewEntity, UUID> {

    List<InterviewEntity> findByApplicationEntity_ApplicationId(UUID id);
    boolean existsByApplicationEntityAndRoundNoAndRoundType(ApplicationEntity applicationEntity, int roundNo, String roundType);

}
