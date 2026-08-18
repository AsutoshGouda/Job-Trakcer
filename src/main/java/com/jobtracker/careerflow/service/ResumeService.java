package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.ResumeNotFoundException;
import com.jobtracker.careerflow.Exception_Handling.UserNotFoundException;
import com.jobtracker.careerflow.entity.ResumeEntity;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.ResumeRepository;
import com.jobtracker.careerflow.repository.UserRepository;
import com.jobtracker.careerflow.requestDTO.ResumeRequestDTO;
import com.jobtracker.careerflow.responseDTO.ResumeResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository){
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    public ResumeResponseDTO mapToResponse(ResumeEntity resumeEntity){
        return new ResumeResponseDTO(
                resumeEntity.getResumeId(),
                resumeEntity.getUrl(),
                resumeEntity.getVersion(),
                resumeEntity.getUploadedAt()
        );
    }

    public ResumeResponseDTO getResumeById(UUID resumeId){
        ResumeEntity resumeEntity = resumeRepository.findById(resumeId).orElseThrow(() -> new ResumeNotFoundException("Resume Not Found!"));
        return mapToResponse(resumeEntity);
    }

    public List<ResumeResponseDTO> getResumesByUserId(UUID userId){
        return resumeRepository.findByUserEntity_UserId(userId).stream().map(this::mapToResponse).toList();
    }

    public List<ResumeResponseDTO> getAllResumes(){
        return resumeRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public ResumeResponseDTO save(ResumeRequestDTO resumeRequestDTO){
        UserEntity userEntity =
                userRepository.getUserByUserId(resumeRequestDTO.userId()).orElseThrow(() -> new UserNotFoundException("User Not Found!"));

        ResumeEntity resumeEntity = new ResumeEntity();
        resumeEntity.setUserEntity(userEntity);
        resumeEntity.setUrl(resumeRequestDTO.url());

        long version = resumeRepository.getVersionForUser(userEntity) + 1;
        resumeEntity.setVersion(version);

        resumeRepository.save(resumeEntity);

        return mapToResponse(resumeEntity);

    }
}
