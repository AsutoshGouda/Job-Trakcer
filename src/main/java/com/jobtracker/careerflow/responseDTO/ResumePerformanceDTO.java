package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResumePerformanceDTO {
    private UUID resumeId;
    private long version;
    private long totalApplications;
    private long totalCompanies;
}
