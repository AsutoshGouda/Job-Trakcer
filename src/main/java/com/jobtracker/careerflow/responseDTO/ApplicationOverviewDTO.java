package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ApplicationOverviewDTO {
    private long totalApplications;
    private long totalCompanies;
    private long totalInterviews;
    private long upcomingInterviews;
}
