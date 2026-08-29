package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.ApplicationRequestDTO;
import com.jobtracker.careerflow.requestDTO.UpdateApplicationRequestDTO;
import com.jobtracker.careerflow.responseDTO.ApplicationResponseDTO;
import com.jobtracker.careerflow.service.ApplicationService;
import jakarta.validation.Valid;
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
    public List<ApplicationResponseDTO> getAllApplications(){
        return applicationService.getAllApplications();
    }

    @GetMapping("/id/{id}")
    public ApplicationResponseDTO getApplicationById(@PathVariable UUID id){
        return applicationService.getApplicationById(id);
    }

    @GetMapping("/user/{id}")
    public List<ApplicationResponseDTO> getApplicationsByUserId(@PathVariable UUID id){
        return applicationService.getApplicationsByUserEntity_UserId(id);
    }

    @GetMapping("/job/{id}")
    public List<ApplicationResponseDTO> getApplicationsByJobId(@PathVariable UUID id){
        return applicationService.getApplicationsByJobEntity_JobId(id);
    }

    @PostMapping
    public ApplicationResponseDTO addApplication(@Valid @RequestBody ApplicationRequestDTO applicationRequestDTO){
        return applicationService.save(applicationRequestDTO);
    }

    @PatchMapping("/updateApplication/id/{id}")
    public ApplicationResponseDTO updateApplication(@PathVariable UUID id, @RequestBody UpdateApplicationRequestDTO date){
        return applicationService.updateApplied(id, date);
    }

    @DeleteMapping("/deleteApplication/id/{id}")
    public void deleteApplication(@PathVariable UUID id){
        applicationService.deleteApplication(id);
    }
}
