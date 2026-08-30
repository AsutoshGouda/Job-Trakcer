package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, UUID> {

    List<ApplicationEntity> findByUserEntity_UserId(UUID id);
    List<ApplicationEntity> findByJobEntity_JobId(UUID id);
    boolean existsByUserEntity_UserIdAndJobEntity_JobId(UUID userId, UUID jobId);
    boolean existsByResumeEntity_ResumeId(UUID resumeId);
    boolean existsByJobEntity_JobId(UUID jobId);

}
