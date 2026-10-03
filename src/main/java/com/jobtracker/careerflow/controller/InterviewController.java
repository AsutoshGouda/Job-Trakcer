package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.InterviewRequestDTO;
import com.jobtracker.careerflow.responseDTO.InterviewResponseDTO;
import com.jobtracker.careerflow.security.CustomerUserDetails;
import com.jobtracker.careerflow.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService){
        this.interviewService = interviewService;
    }

    @GetMapping("/id/{id}")
    public InterviewResponseDTO getInterviewById(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return interviewService.getInterviewById(userId, id);
    }

    @GetMapping("/application/{id}")
    public List<InterviewResponseDTO> getInterviewByApplicationId(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return interviewService.getInterviewsByApplicationId(userId, id);
    }

    @PostMapping
    public InterviewResponseDTO addInterview(@Valid @RequestBody InterviewRequestDTO interviewRequestDTO, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return interviewService.save(userId, interviewRequestDTO);
    }

    @PatchMapping("/updateInterview/id/{id}")
    public InterviewResponseDTO updateInterview(@PathVariable UUID id, @Valid @RequestBody InterviewRequestDTO interviewRequestDTO, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return interviewService.updateInterview(userId, id,interviewRequestDTO);
    }

    @DeleteMapping("/deleteInterview/id/{interviewId}")
    public void deleteInterview(@PathVariable UUID interviewId, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        interviewService.deleteInterview(userId, interviewId);
    }

}
