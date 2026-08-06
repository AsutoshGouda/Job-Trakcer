package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.ResumeNotFoundException;
import com.jobtracker.careerflow.entity.ResumeEntity;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.ResumeRepository;
import com.jobtracker.careerflow.repository.UserRepository;
import com.jobtracker.careerflow.responseDTO.ResumeResponseDTO;
import com.jobtracker.careerflow.resquestDTO.ResumeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class resumeService {

    @Autowired
    private final ResumeRepository resumeRepository;

    @Autowired
    private final UserRepository userRepository;

    public resumeService(ResumeRepository resumeRepository, UserRepository userRepository){
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    public ResumeResponseDTO mapToEntity(ResumeEntity resumeEntity){

        return new ResumeResponseDTO(
                resumeEntity.getUser().getFirstName(),
                resumeEntity.getUser().getLastName(),
                resumeEntity.getPath(),
                resumeEntity.getVersion(),
                resumeEntity.getUploadedAt()
        );
    }

    public List<ResumeEntity> getAllResume(){
        return resumeRepository.findAll();
    }

    public ResumeResponseDTO getLatestResumeByUserId(UUID userId){
        ResumeEntity resumeEntity = resumeRepository.getResumeByResumeId(userId).orElseThrow(() -> new ResumeNotFoundException("Resume Not Found"));
        return mapToEntity(resumeEntity);
    }

    public ResumeResponseDTO getResumeByResumeId(UUID resumeId){
        ResumeEntity resumeEntity = resumeRepository.getResumeByResumeId(resumeId).orElseThrow(() -> new ResumeNotFoundException("Resume Not Found"));
        return mapToEntity(resumeEntity);
    }

    public List<ResumeResponseDTO> getAllResumesByUserId(UUID userId){
        List<ResumeEntity> resumeEntities = resumeRepository.findByUser_UserId(userId);
        if(resumeEntities.isEmpty()){
            throw new ResumeNotFoundException("Resume not Found!!!");
        }
        return resumeEntities.stream().map(this::mapToEntity).toList();
    }

    public ResumeResponseDTO save(UUID userId, ResumeRequestDTO resumeRequestDTO){
        UserEntity userEntity = userRepository.getUserByUserId(userId).orElseThrow(() -> new ResumeNotFoundException("User Not Found!!"));
        ResumeEntity resumeEntity = new ResumeEntity();
        resumeEntity.setPath(resumeRequestDTO.fileUrl());
        resumeRepository.save(resumeEntity);
        return mapToEntity(resumeEntity);
    }

    public ResumeResponseDTO updateResume(UUID userId, ResumeRequestDTO resumeRequestDTO){
        List<ResumeEntity> resumeEntities = resumeRepository.findByUser_UserId(userId);
        if(resumeEntities.isEmpty()){
            throw new ResumeNotFoundException("There is no resume to update. Upload a resume.");
        }
        ResumeEntity existingResume = resumeEntities.get(0);
        existingResume.setPath(resumeRequestDTO.fileUrl());
        existingResume.setVersion(existingResume.getVersion() + 1);
        return mapToEntity(resumeRepository.save(existingResume));
    }

    public ResumeResponseDTO deleteResume(UUID userId){
        List<ResumeEntity> 
    }
}
