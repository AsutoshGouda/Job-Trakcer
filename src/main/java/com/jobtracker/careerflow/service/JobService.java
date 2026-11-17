package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.ApplicationExistsForThisJobIdException;
import com.jobtracker.careerflow.Exception_Handling.CompanyNotFoundException;
import com.jobtracker.careerflow.Exception_Handling.InvalidAttributeException;
import com.jobtracker.careerflow.Exception_Handling.JobNotFoundException;
import com.jobtracker.careerflow.Exception_Handling.UserNotFoundException;
import com.jobtracker.careerflow.entity.CompanyEntity;
import com.jobtracker.careerflow.entity.JobEntity;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.ApplicationRepository;
import com.jobtracker.careerflow.repository.CompanyRepository;
import com.jobtracker.careerflow.repository.JobRepository;
import com.jobtracker.careerflow.repository.UserRepository;
import com.jobtracker.careerflow.requestDTO.JobRequestDTO;
import com.jobtracker.careerflow.requestDTO.UpdateJobRequestDTO;
import com.jobtracker.careerflow.responseDTO.JobResponseDTO;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, CompanyRepository companyRepository, ApplicationRepository applicationRepository, UserRepository userRepository){
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public JobResponseDTO mapToResponse(JobEntity jobEntity){
        return new JobResponseDTO(
                jobEntity.getJobId(),
                jobEntity.getCompanyEntity() != null
                        ? jobEntity.getCompanyEntity().getCompanyId()
                        : null,
                jobEntity.getCompanyNameOther(),
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

    public JobResponseDTO getJobById(UUID userId, UUID id){
        JobEntity jobEntity = jobRepository.findByJobIdAndUserEntity_UserId(id, userId).orElseThrow(()-> new JobNotFoundException("Job Not Found!"));
        return mapToResponse(jobEntity);
    }

    public List<JobResponseDTO> getJobsByCompanyId(UUID userId, UUID companyId){
        List<JobEntity> jobEntities = jobRepository.findByCompanyEntity_CompanyIdAndUserEntity_UserId(companyId, userId);
        return jobEntities.stream().map(this::mapToResponse).toList();
    }

    public JobResponseDTO save(UUID userId, JobRequestDTO jobRequestDTO){
        if (jobRequestDTO.companyId() == null &&
                (jobRequestDTO.companyNameOther() == null || jobRequestDTO.companyNameOther().isBlank())) {

            throw new IllegalArgumentException("Company name cannot be blank.");
        }

        if (jobRequestDTO.companyId() != null &&
                jobRequestDTO.companyNameOther() != null) {

            throw new IllegalArgumentException("Provide either companyId or companyNameOther, not both.");
        }

        JobEntity jobEntity = new JobEntity();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User Not Found!"));
        jobEntity.setUserEntity(userEntity);

        if(jobRequestDTO.companyId() != null){
            CompanyEntity companyEntity = companyRepository.findById(jobRequestDTO.companyId())
                    .orElseThrow(() -> new CompanyNotFoundException("Company Not Found!"));

            jobEntity.setCompanyEntity(companyEntity);
        }else{
            jobEntity.setCompanyNameOther(jobRequestDTO.companyNameOther().trim());
        }

        jobEntity.setTitle(jobRequestDTO.title());
        jobEntity.setPostedAt(jobRequestDTO.postedAt());
        jobEntity.setUrl(jobRequestDTO.url());
        jobEntity.setStatus("OPEN");

        jobRepository.save(jobEntity);
        return mapToResponse(jobEntity);
    }

    public JobResponseDTO updateJob(UUID userId, UUID jobId, UpdateJobRequestDTO updateJobRequestDTO){
        JobEntity jobEntity = jobRepository.findByJobIdAndUserEntity_UserId(jobId, userId)
                .orElseThrow(() -> new JobNotFoundException("Job Not Found!"));

        if(updateJobRequestDTO.companyId() != null &&
                updateJobRequestDTO.companyNameOther() != null){
            throw new IllegalArgumentException(
                    "Provide either companyId or companyNameOther, not both."
            );
        }

        if(updateJobRequestDTO.companyId() != null){
            CompanyEntity companyEntity = companyRepository.findById(updateJobRequestDTO.companyId())
                    .orElseThrow(() -> new CompanyNotFoundException("Company Not Found!"));

            jobEntity.setCompanyEntity(companyEntity);
            jobEntity.setCompanyNameOther(null);
        }

        if(updateJobRequestDTO.companyNameOther() != null){
            if(updateJobRequestDTO.companyNameOther().isBlank()){
                throw new IllegalArgumentException("Company name cannot be blank.");
            }

            jobEntity.setCompanyEntity(null);
            jobEntity.setCompanyNameOther(updateJobRequestDTO.companyNameOther().trim());
        }

        if(updateJobRequestDTO.status() != null && !updateJobRequestDTO.status().isBlank()){
            if(!updateJobRequestDTO.status().equals("OPEN") &&
                    !updateJobRequestDTO.status().equals("CLOSED") &&
                    !updateJobRequestDTO.status().equals("EXPIRED")){
                throw new InvalidAttributeException("Invalid status.");
            }
            jobEntity.setStatus(updateJobRequestDTO.status());
        }

        if(updateJobRequestDTO.url() != null && !updateJobRequestDTO.url().isBlank()){
            jobEntity.setUrl(updateJobRequestDTO.url());
        }

        if(updateJobRequestDTO.title() != null && !updateJobRequestDTO.title().isBlank()){
            jobEntity.setTitle(updateJobRequestDTO.title());
        }

        jobRepository.save(jobEntity);
        return mapToResponse(jobEntity);
    }

    public void deleteJob(UUID userId, UUID jobId){
        JobEntity jobEntity = jobRepository.findByJobIdAndUserEntity_UserId(jobId, userId).orElseThrow(()-> new JobNotFoundException("Job Not Found!"));

        if(applicationRepository.existsByUserEntity_UserIdAndJobEntity_JobId(userId, jobId)){
            throw new ApplicationExistsForThisJobIdException("For this job, applications exist!");
        }
        jobRepository.delete(jobEntity);
    }

}
