package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardResponseDTO {
    private ApplicationOverviewDTO overview;
    private List<ApplicationStatusDTO> applicationStatuses;
    private List<CompanyApplicationDTO> companyApplications;
    private List<InterviewRoundDTO> interviewRounds;
    private List<ResumePerformanceDTO> resumePerformance;
}
