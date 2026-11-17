package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    List<JobEntity> findByCompanyEntity_CompanyId(UUID companyId);

    List<JobEntity> findByCompanyEntity_CompanyName(String companyName);

    Optional<JobEntity> findByJobIdAndUserEntity_UserId(
            UUID jobId, UUID userId);

    List<JobEntity> findByUserEntity_UserId(UUID userId);

    List<JobEntity> findByCompanyEntity_CompanyIdAndUserEntity_UserId(
            UUID companyId, UUID userId);

}
