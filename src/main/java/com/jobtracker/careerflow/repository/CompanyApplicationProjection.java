package com.jobtracker.careerflow.repository;

import java.util.UUID;

public interface CompanyApplicationProjection {
    UUID getCompanyId();
    String getCompanyName();
    Long getTotalApplications();
}
