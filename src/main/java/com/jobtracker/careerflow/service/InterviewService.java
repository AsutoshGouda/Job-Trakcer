package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.ApplicationNotFoundException;
import com.jobtracker.careerflow.Exception_Handling.InterviewExistsException;
import com.jobtracker.careerflow.Exception_Handling.InterviewNotFoundException;
import com.jobtracker.careerflow.entity.ApplicationEntity;
import com.jobtracker.careerflow.entity.InterviewEntity;
import com.jobtracker.careerflow.repository.ApplicationRepository;
import com.jobtracker.careerflow.repository.InterviewRepository;
import com.jobtracker.careerflow.requestDTO.InterviewRequestDTO;
import com.jobtracker.careerflow.requestDTO.InterviewUpdateRequestDTO;
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

    public InterviewResponseDTO save(UUID userId, InterviewRequestDTO interviewRequestDTO){

        ApplicationEntity applicationEntity =
                applicationRepository.findById(interviewRequestDTO.applicationId()).orElseThrow(()-> new ApplicationNotFoundException("Application Not Found"));

        if(!userId.equals(applicationEntity.getUserEntity().getUserId())){
            throw new ApplicationNotFoundException("Application Not Found");
        }

        if(interviewRepository.existsByApplicationEntityAndRoundNo(applicationEntity,
                interviewRequestDTO.roundNo())){
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

    public InterviewResponseDTO getInterviewById(UUID userId, UUID id){
        InterviewEntity interviewEntity =
                interviewRepository.findById(id).orElseThrow(()->new InterviewNotFoundException("Interview Not " +
                        "Found!"));
        if(!userId.equals(interviewEntity.getApplicationEntity().getUserEntity().getUserId())){
            throw new InterviewNotFoundException("Interview Not Found!");
        }
        return mapToResponse(interviewEntity);
    }

    public List<InterviewResponseDTO> getInterviewsByApplicationId(UUID userId, UUID id){
        return interviewRepository.findByApplicationEntity_ApplicationIdAndApplicationEntity_UserEntity_UserId(id, userId).stream().map(this::mapToResponse).toList();
    }

    public InterviewResponseDTO updateInterview(UUID userId, UUID id, InterviewUpdateRequestDTO interviewUpdateRequestDTO){
        InterviewEntity interviewEntity =
                interviewRepository.findById(id).orElseThrow(()-> new InterviewNotFoundException("Interview not found"));
        if(!userId.equals(interviewEntity.getApplicationEntity().getUserEntity().getUserId())){
            throw new InterviewNotFoundException("Interview not found");
        }

        if(interviewUpdateRequestDTO.roundType() != null && !interviewUpdateRequestDTO.roundType().isBlank()){
            interviewEntity.setRoundType(interviewUpdateRequestDTO.roundType());
        }
        if(interviewUpdateRequestDTO.interviewMode() != null && !interviewUpdateRequestDTO.interviewMode().isBlank()){
            interviewEntity.setInterviewMode(interviewUpdateRequestDTO.interviewMode());
        }
        if(interviewUpdateRequestDTO.roundNo() != null && interviewUpdateRequestDTO.roundNo() > 0 && interviewUpdateRequestDTO.roundNo() != interviewEntity.getRoundNo() ){
            if(interviewRepository.existsByApplicationEntityAndRoundNoAndInterviewIdNot(interviewEntity.getApplicationEntity(),interviewUpdateRequestDTO.roundNo(), id)){
                throw new InterviewExistsException("Interview Already Exists");
            }
            interviewEntity.setRoundNo(interviewUpdateRequestDTO.roundNo());
        }
        if(interviewUpdateRequestDTO.scheduledAt() != null){
            interviewEntity.setScheduledAt(interviewUpdateRequestDTO.scheduledAt());
        }
        interviewRepository.save(interviewEntity);
        return mapToResponse(interviewEntity);
    }

    public void deleteInterview(UUID userId, UUID id){
        InterviewEntity interviewEntity =
                interviewRepository.findById(id).orElseThrow(()-> new InterviewNotFoundException("Interview Not " +
                        "Found!"));
        if(!userId.equals(interviewEntity.getApplicationEntity().getUserEntity().getUserId())){
            throw new InterviewNotFoundException("Interview not found");
        }
        interviewRepository.delete(interviewEntity);
    }
}
