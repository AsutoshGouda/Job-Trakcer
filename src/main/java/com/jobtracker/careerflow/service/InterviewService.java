package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.ApplicationNotFoundException;
import com.jobtracker.careerflow.Exception_Handling.InterviewExistsException;
import com.jobtracker.careerflow.Exception_Handling.InterviewNotFoundException;
import com.jobtracker.careerflow.entity.ApplicationEntity;
import com.jobtracker.careerflow.entity.InterviewEntity;
import com.jobtracker.careerflow.repository.ApplicationRepository;
import com.jobtracker.careerflow.repository.InterviewRepository;
import com.jobtracker.careerflow.requestDTO.InterviewRequestDTO;
import com.jobtracker.careerflow.responseDTO.InterviewResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewService(InterviewRepository interviewRepository, ApplicationRepository applicationRepository){
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
    }

    public InterviewResponseDTO mapToResponse(InterviewEntity interviewEntity){
        return new InterviewResponseDTO(

            interviewEntity.getInterviewId(),
            interviewEntity.getApplicationEntity().getApplicationId(),
            interviewEntity.getScheduledAt(),
            interviewEntity.getInterviewMode(),
            interviewEntity.getRoundNo(),
            interviewEntity.getRoundType(),
            interviewEntity.getStatus(),
            interviewEntity.getCreatedAt(),
            interviewEntity.getUpdatedAt()
        );
    }

    public InterviewResponseDTO save(InterviewRequestDTO interviewRequestDTO){

        System.out.println("InterviewService.save() reached");

        ApplicationEntity applicationEntity =
                applicationRepository.findById(interviewRequestDTO.applicationId()).orElseThrow(()-> new ApplicationNotFoundException("Application Not Found"));

        if(interviewRepository.existsByApplicationEntityAndRoundNoAndRoundType(applicationEntity,
                interviewRequestDTO.roundNo(), interviewRequestDTO.roundType())){
            throw new InterviewExistsException("Interview details are duplicate!");
        }

        InterviewEntity interviewEntity = new InterviewEntity();
        interviewEntity.setApplicationEntity(applicationEntity);
        interviewEntity.setScheduledAt(interviewRequestDTO.scheduledAt());
        interviewEntity.setInterviewMode(interviewRequestDTO.interviewMode());
        interviewEntity.setRoundNo(interviewRequestDTO.roundNo());
        interviewEntity.setRoundType(interviewRequestDTO.roundType());
        interviewEntity.setStatus("SCHEDULED");
        interviewRepository.save(interviewEntity);
        return mapToResponse(interviewEntity);

    }

    public InterviewResponseDTO getInterviewById(UUID id){
        InterviewEntity interviewEntity =
                interviewRepository.findById(id).orElseThrow(()->new InterviewNotFoundException("Interview Not " +
                        "Found!"));
        return mapToResponse(interviewEntity);
    }

    public List<InterviewResponseDTO> getAllInterviews(){
        return interviewRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public List<InterviewResponseDTO> getInterviewsByApplicationId(UUID id){
        return interviewRepository.findByApplicationEntity_ApplicationId(id).stream().map(this::mapToResponse).toList();
    }

    public InterviewResponseDTO updateInterview(UUID id, InterviewRequestDTO interviewRequestDTO){
        InterviewEntity interviewEntity =
                interviewRepository.findById(id).orElseThrow(()-> new InterviewNotFoundException("Interview not found"));
        if(!interviewRequestDTO.roundType().isEmpty()){
            interviewEntity.setRoundType(interviewRequestDTO.roundType());
        }
        if(!interviewRequestDTO.interviewMode().isEmpty()){
            interviewEntity.setInterviewMode(interviewRequestDTO.interviewMode());
        }
        if(interviewRequestDTO.roundNo() != 0){
            interviewEntity.setRoundNo(interviewRequestDTO.roundNo());
        }
        if(interviewRequestDTO.scheduledAt() != null){
            interviewEntity.setScheduledAt(interviewRequestDTO.scheduledAt());
        }
        interviewRepository.save(interviewEntity);
        return mapToResponse(interviewEntity);
    }

    public void deleteInterview(UUID interviewId){
        InterviewEntity interviewEntity =
                interviewRepository.findById(interviewId).orElseThrow(()-> new InterviewNotFoundException("Interview Not Found!"));
        interviewRepository.delete(interviewEntity);
    }
}
