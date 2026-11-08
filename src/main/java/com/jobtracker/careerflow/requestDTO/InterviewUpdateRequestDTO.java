package com.jobtracker.careerflow.requestDTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record InterviewUpdateRequestDTO(
        OffsetDateTime scheduledAt,
        @Pattern(regexp = "^(ONLINE|PHONE|ONSITE)$", message = "Invalid Value") String interviewMode,
        @Positive Integer roundNo,
        @Pattern(regexp = "^(HR_SCREENING|TECHNICAL|SALARY_NEGOTIATION)$", message = "Invalid Value") String roundType
) {
}
