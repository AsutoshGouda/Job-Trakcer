package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.entity.ResumeEntity;
import com.jobtracker.careerflow.requestDTO.ResumeRequestDTO;
import com.jobtracker.careerflow.responseDTO.ResumeResponseDTO;
import com.jobtracker.careerflow.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService){
        this.resumeService = resumeService;
    }

    @GetMapping
    public List<ResumeResponseDTO> getAllResumes(){
        return resumeService.getAllResumes();
    }

    @GetMapping("/user/{id}")
    public List<ResumeResponseDTO> getResumeByUserId(@PathVariable UUID id){
        return resumeService.getResumesByUserId(id);
    }

    @GetMapping("/id/{id}")
    public ResumeResponseDTO getResumeByResumeId(@PathVariable UUID id){
        return resumeService.getResumeById(id);
    }

    @PostMapping("/addResume")
    public ResumeResponseDTO addResume(@Valid @RequestBody ResumeRequestDTO resumeRequestDTO){
        return resumeService.save(resumeRequestDTO);
    }
}
