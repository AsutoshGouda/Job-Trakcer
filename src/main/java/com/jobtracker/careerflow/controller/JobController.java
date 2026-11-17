package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.JobRequestDTO;
import com.jobtracker.careerflow.requestDTO.UpdateJobRequestDTO;
import com.jobtracker.careerflow.responseDTO.JobResponseDTO;
import com.jobtracker.careerflow.security.CustomerUserDetails;
import com.jobtracker.careerflow.service.JobService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/id/{id}")
    public JobResponseDTO getJobById(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){

        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;

        UUID loggedInUserId = customerUserDetails.getUserId();

        return jobService.getJobById(loggedInUserId, id);
    }

    @GetMapping("/company/{id}")
    public List<JobResponseDTO> getJobsByCompanyId(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID loggedInUserId = customerUserDetails.getUserId();
        return jobService.getJobsByCompanyId(loggedInUserId, id);
    }

    @PostMapping
    public JobResponseDTO addJob(@Valid @RequestBody JobRequestDTO jobRequestDTO,
                                 @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID loggedInUserId = customerUserDetails.getUserId();
        return jobService.save(loggedInUserId, jobRequestDTO);
    }

    @PatchMapping("/updateJob/id/{jobId}")
    public JobResponseDTO updateJob(@PathVariable UUID jobId,
                                    @Valid @RequestBody UpdateJobRequestDTO updateJobRequestDTO,
                                    @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID loggedInUserId = customerUserDetails.getUserId();
        return jobService.updateJob(loggedInUserId, jobId, updateJobRequestDTO);
    }

    @DeleteMapping("/deleteJob/id/{jobId}")
    public void deleteJob(@PathVariable UUID jobId, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID loggedInUserId = customerUserDetails.getUserId();
        jobService.deleteJob(loggedInUserId, jobId);
    }

}
