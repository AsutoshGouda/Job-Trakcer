package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.repository.*;
import com.jobtracker.careerflow.responseDTO.*;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DashboardService {

    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;

    public DashboardService(ApplicationRepository applicationRepository, InterviewRepository interviewRepository){
        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
    }

    public ApplicationOverviewDTO getOverview(UUID userId){
        long totalApplications = applicationRepository.countByUserEntity_UserId(userId);
        long totalCompanies = applicationRepository.countCompanies(userId);
        long totalInterviews = interviewRepository.countByApplicationEntity_UserEntity_UserId(userId);
        long upcomingInterviews = interviewRepository.countByApplicationEntity_UserEntity_UserIdAndScheduledAtAfter(userId, OffsetDateTime.now());
        return new ApplicationOverviewDTO(
                totalApplications,
                totalCompanies,
                totalInterviews,
                upcomingInterviews
        );
    }

    public List<ApplicationStatusDTO> getApplicationStatuses(UUID userId){
        List<ApplicationStatusProjection> projections = applicationRepository.countApplicationsByStatus(userId);
        return projections.stream().map(p -> new ApplicationStatusDTO(p.getStatus(), p.getCount())).toList();
    }

    public List<CompanyApplicationDTO> getCompanyApplications(UUID userId){
        List<CompanyApplicationProjection> projections = applicationRepository.findCompanyApplicationStats(userId);
        return projections.stream().map(p -> new CompanyApplicationDTO(p.getCompanyId(), p.getCompanyName(),
                p.getTotalApplications())).toList();
    }

    public List<InterviewRoundDTO> getInterviewRounds(UUID userId){
        List<InterviewRoundProjection> projections = interviewRepository.findInterviewRoundStats(userId);
        return projections.stream().map(p -> new InterviewRoundDTO(p.getRoundType(), p.getRoundNo(),
                p.getCount())).toList();
    }

    public List<ResumePerformanceDTO> getResumePerformance (UUID userId){
        List<ResumeApplicationProjection> projections = applicationRepository.findResumeApplicationStats(userId);
        return projections.stream()
                .map(p -> new ResumePerformanceDTO(
                        p.getResumeId(),
                        p.getVersion(),
                        p.getTotalApplications(),
                        p.getTotalCompanies()
                ))
                .toList();
    }
}
