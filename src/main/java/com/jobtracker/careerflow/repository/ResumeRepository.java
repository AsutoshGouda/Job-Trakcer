package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.ResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResumeRepository extends JpaRepository<ResumeEntity,UUID> {
    List<ResumeEntity> findAll();
    Optional<ResumeEntity> getResumeByResumeId(UUID resumeId);
    List<ResumeEntity> findByUser_UserId(UUID userId);

}
