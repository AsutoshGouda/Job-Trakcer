package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    List<JobEntity> findByCompanyEntity_CompanyId(UUID companyId);

    List<JobEntity> findByCompanyEntity_CompanyName(String companyName);

}
