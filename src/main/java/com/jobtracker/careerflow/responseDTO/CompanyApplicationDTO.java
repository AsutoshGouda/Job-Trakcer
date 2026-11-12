package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CompanyApplicationDTO {
    private UUID companyId;
    private String companyName;
    private long totalApplications;
}
