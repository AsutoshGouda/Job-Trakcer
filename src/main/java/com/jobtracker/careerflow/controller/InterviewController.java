package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.InterviewRequestDTO;
import com.jobtracker.careerflow.responseDTO.InterviewResponseDTO;
import com.jobtracker.careerflow.service.InterviewService;
import jakarta.validation.Valid;
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

    @GetMapping
    public List<InterviewResponseDTO> getAllInterviews(){
        return interviewService.getAllInterviews();
    }

    @GetMapping("/id/{id}")
    public InterviewResponseDTO getInterviewById(@PathVariable UUID id){
        return interviewService.getInterviewById(id);
    }

    @GetMapping("/application/{id}")
    public List<InterviewResponseDTO> getInterviewByApplicationId(@PathVariable UUID id){
        return interviewService.getInterviewsByApplicationId(id);
    }

    @PostMapping
    public InterviewResponseDTO addInterview(@Valid @RequestBody InterviewRequestDTO interviewRequestDTO){
        return interviewService.save(interviewRequestDTO);
    }

    @PatchMapping("/updateInterview/id/{id}")
    public InterviewResponseDTO updateInterview(@PathVariable UUID id, @Valid @RequestBody InterviewRequestDTO interviewRequestDTO){
        return interviewService.updateInterview(id,interviewRequestDTO);
    }

    @DeleteMapping("/deleteInterview/id/{id}")
    public void deleteInterview(@PathVariable UUID interviewId){
        interviewService.deleteInterview(interviewId);
    }

}
