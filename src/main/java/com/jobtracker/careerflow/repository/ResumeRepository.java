package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.ResumeEntity;

import java.util.List;
import java.util.UUID;

public interface ResumeRepository {
    List<ResumeEntity> findAll();
    ResumeEntity getResumeById(UUID resumeId);
    ResumeEntity getResumeByUserId(UUID userId);
}
