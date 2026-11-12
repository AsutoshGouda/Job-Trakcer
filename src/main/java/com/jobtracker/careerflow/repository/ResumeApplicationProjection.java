package com.jobtracker.careerflow.repository;

import java.util.UUID;

public interface ResumeApplicationProjection {
    UUID getResumeId();
    Long getVersion();
    Long getTotalApplications();
    Long getTotalCompanies();
}
