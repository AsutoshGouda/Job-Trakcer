package com.jobtracker.careerflow.controller;


import com.jobtracker.careerflow.responseDTO.DashboardResponseDTO;
import com.jobtracker.careerflow.security.CustomerUserDetails;
import com.jobtracker.careerflow.service.DashboardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponseDTO getOverview(@AuthenticationPrincipal CustomerUserDetails userDetails){
        UUID userId = userDetails.getUserId();

        var overview = dashboardService.getOverview(userId);
        var applicationStatuses = dashboardService.getApplicationStatuses(userId);
        var companyApplications = dashboardService.getCompanyApplications(userId);
        var interviewRounds = dashboardService.getInterviewRounds(userId);
        var resumePerformance = dashboardService.getResumePerformance(userId);

        return new DashboardResponseDTO(
                overview, applicationStatuses, companyApplications, interviewRounds, resumePerformance
        );
    }
}
