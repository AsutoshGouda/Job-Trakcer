package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.CompanyNotFoundException;
import com.jobtracker.careerflow.Exception_Handling.JobNotFoundException;
import com.jobtracker.careerflow.entity.CompanyEntity;
import com.jobtracker.careerflow.entity.JobEntity;
import com.jobtracker.careerflow.repository.CompanyRepository;
import com.jobtracker.careerflow.repository.JobRepository;
import com.jobtracker.careerflow.requestDTO.JobRequestDTO;
import com.jobtracker.careerflow.responseDTO.JobResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository, CompanyRepository companyRepository){
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public JobResponseDTO mapToResponse(JobEntity jobEntity){
        return new JobResponseDTO(
                jobEntity.getJobId(),
                jobEntity.getCompanyEntity().getCompanyId(),
                jobEntity.getTitle(),
                jobEntity.getPostedAt(),
                jobEntity.getStatus(),
                jobEntity.getUrl(),
                jobEntity.getCreatedAt(),
                jobEntity.getUpdatedAt()
        );
    }

    public List<JobResponseDTO> getAllJobs(){
        return jobRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public JobResponseDTO getJobById(UUID id){
        JobEntity jobEntity = jobRepository.findById(id).orElseThrow(()->new JobNotFoundException("Job Not Found!"));
        return mapToResponse(jobEntity);
    }

    public List<JobResponseDTO> getJobsByCompanyId(UUID id){
        List<JobEntity> jobEntities = jobRepository.findByCompanyEntity_CompanyId(id);
        return jobEntities.stream().map(this::mapToResponse).toList();
    }

    public JobResponseDTO save(JobRequestDTO jobRequestDTO){
        JobEntity jobEntity = new JobEntity();
        CompanyEntity companyEntity = companyRepository.findById(jobRequestDTO.companyId()).orElseThrow(() -> new CompanyNotFoundException("Company Not Found!"));
        jobEntity.setCompanyEntity(companyEntity);
        jobEntity.setTitle(jobRequestDTO.title());
        jobEntity.setPostedAt(jobRequestDTO.postedAt());
        jobEntity.setUrl(jobRequestDTO.url());
        jobEntity.setStatus("OPEN");
        jobRepository.save(jobEntity);
        return mapToResponse(jobEntity);
    }
}
