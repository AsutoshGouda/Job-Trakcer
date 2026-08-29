package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.*;
import com.jobtracker.careerflow.entity.ApplicationEntity;
import com.jobtracker.careerflow.entity.JobEntity;
import com.jobtracker.careerflow.entity.ResumeEntity;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.*;
import com.jobtracker.careerflow.requestDTO.ApplicationRequestDTO;
import com.jobtracker.careerflow.requestDTO.UpdateApplicationRequestDTO;
import com.jobtracker.careerflow.responseDTO.ApplicationResponseDTO;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ResumeRepository resumeRepository;
    private final InterviewRepository interviewRepository;

    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository,
                              JobRepository jobRepository, ResumeRepository resumeRepository, InterviewRepository interviewRepository){
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.resumeRepository = resumeRepository;
        this.interviewRepository = interviewRepository;
    }

    public ApplicationResponseDTO mapToResponse(ApplicationEntity applicationEntity){
        return new ApplicationResponseDTO(
                applicationEntity.getResumeEntity().getResumeId(),
                applicationEntity.getJobEntity().getJobId(),
                applicationEntity.getUserEntity().getUserId(),
                applicationEntity.getStatus(),
                applicationEntity.getAppliedAt(),
                applicationEntity.getCreatedAt(),
                applicationEntity.getUpdatedAt()
        );
    }

    public ApplicationResponseDTO save(ApplicationRequestDTO applicationRequestDTO){
        ResumeEntity resumeEntity =
                resumeRepository.findById(applicationRequestDTO.resumeId()).orElseThrow(() -> new ResumeNotFoundException(
                        "Resume Not Found!"));

        JobEntity jobEntity =
                jobRepository.findById(applicationRequestDTO.jobId()).orElseThrow(()-> new JobNotFoundException("Job Not Found!"));

        UserEntity userEntity =
                userRepository.findById(applicationRequestDTO.userId()).orElseThrow(() -> new UserNotFoundException("User " +
                        "Not Found!"));

        UserEntity resumeOwner = resumeEntity.getUserEntity();

        if(!userEntity.equals(resumeOwner)){
            throw new ResumeOwnershipException("This Resume is not the user's actual resume.");
        }

        if(applicationRepository.existsByUserEntity_UserIdAndJobEntity_JobId(userEntity.getUserId(),
                jobEntity.getJobId())){
            throw new ApplicationAlreadyExistsException("Application already exists!");
        }

        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setResumeEntity(resumeEntity);
        applicationEntity.setJobEntity(jobEntity);
        applicationEntity.setUserEntity(userEntity);
        applicationEntity.setStatus("APPLIED");
        applicationEntity.setAppliedAt(applicationRequestDTO.appliedAt());
        applicationRepository.save(applicationEntity);
        return mapToResponse(applicationEntity);

    }

    public List<ApplicationResponseDTO> getAllApplications(){
        return applicationRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public ApplicationResponseDTO getApplicationById(UUID id){
        ApplicationEntity applicationEntity =
                applicationRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException(
                "Application Does not exists!"));
        return mapToResponse(applicationEntity);
    }

    public  List<ApplicationResponseDTO> getApplicationsByUserEntity_UserId(UUID id){
        return applicationRepository.findByUserEntity_UserId(id).stream().map(this::mapToResponse).toList();
    }

    public List<ApplicationResponseDTO> getApplicationsByJobEntity_JobId(UUID id){
        return applicationRepository.findByJobEntity_JobId(id).stream().map(this::mapToResponse).toList();
    }

    public ApplicationResponseDTO updateApplied(UUID id, UpdateApplicationRequestDTO updatedAppliedAt){
        ApplicationEntity applicationEntity = applicationRepository.findById(id).orElseThrow(()-> new ApplicationNotFoundException("Application Not Found!"));
        System.out.println("OLD: " + applicationEntity.getAppliedAt());
        System.out.println("NEW: " + updatedAppliedAt.appliedAt());
        applicationEntity.setAppliedAt(updatedAppliedAt.appliedAt());
        applicationRepository.save(applicationEntity);
        System.out.println("AFTER: " + applicationEntity.getAppliedAt());
        return mapToResponse(applicationEntity);
    }

    public void deleteApplication(UUID id){
        ApplicationEntity applicationEntity =
                applicationRepository.findById(id).orElseThrow(()-> new ApplicationNotFoundException("Application Not Found!"));
        if(interviewRepository.existsByApplicationEntity(applicationEntity)){
            throw new ApplicationHasInterviewsException("Interview exists for this application!");
        }
        applicationRepository.delete(applicationEntity);
    }

}
