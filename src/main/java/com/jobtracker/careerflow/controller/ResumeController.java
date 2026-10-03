package com.jobtracker.careerflow.controller;


import com.jobtracker.careerflow.requestDTO.ResumeRequestDTO;
import com.jobtracker.careerflow.responseDTO.ResumeResponseDTO;
import com.jobtracker.careerflow.security.CustomerUserDetails;
import com.jobtracker.careerflow.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public List<ResumeResponseDTO> getAllResumes(@AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return resumeService.getAllResumes(userId);
    }

    @GetMapping("/user")
    public List<ResumeResponseDTO> getResumeByUserId(@AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return resumeService.getResumesByUserId(userId);
    }

    @GetMapping("/id/{id}")
    public ResumeResponseDTO getResumeByResumeId(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return resumeService.getResumeById(userId, id);
    }

    @PostMapping("/addResume")
    public ResumeResponseDTO addResume(@Valid @RequestBody ResumeRequestDTO resumeRequestDTO, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return resumeService.save(userId, resumeRequestDTO);
    }

    @DeleteMapping("/deleteResume/id/{id}")
    public void deleteResume(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        resumeService.deleteResume(userId, id);
    }

}
