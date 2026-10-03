package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.ApplicationRequestDTO;
import com.jobtracker.careerflow.requestDTO.UpdateApplicationRequestDTO;
import com.jobtracker.careerflow.responseDTO.ApplicationResponseDTO;
import com.jobtracker.careerflow.security.CustomerUserDetails;
import com.jobtracker.careerflow.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<ApplicationResponseDTO> getAllApplications(@AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return applicationService.getAllApplications(userId);
    }

    @GetMapping("/id/{id}")
    public ApplicationResponseDTO getApplicationById(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return applicationService.getApplicationById(userId, id);
    }

    @GetMapping("/user")
    public List<ApplicationResponseDTO> getMyApplications(@AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return applicationService.getApplicationsByUserEntity_UserId(userId);
    }

    @GetMapping("/job/{id}")
    public List<ApplicationResponseDTO> getApplicationsByJobId(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return applicationService.getApplicationsByJobEntity_JobId(userId, id);
    }

    @PostMapping
    public ApplicationResponseDTO addApplication(@Valid @RequestBody ApplicationRequestDTO applicationRequestDTO, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return applicationService.save(applicationRequestDTO, userId);
    }

    @PatchMapping("/updateApplication/id/{id}")
    public ApplicationResponseDTO updateApplication(@PathVariable UUID id,
                                                    @RequestBody UpdateApplicationRequestDTO date,
                                                    @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return applicationService.updateApplied(id, userId, date);
    }

    @DeleteMapping("/deleteApplication/id/{id}")
    public void deleteApplication(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        applicationService.deleteApplication(userId, id);
    }
}
