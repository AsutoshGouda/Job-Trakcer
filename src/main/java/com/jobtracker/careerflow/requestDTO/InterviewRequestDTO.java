package com.jobtracker.careerflow.requestDTO;

import java.time.OffsetDateTime;
import java.util.UUID;

public record InterviewRequestDTO(
        UUID applicationId,
        OffsetDateTime scheduledAt,
        String interviewMode,
        int roundNo,
        String roundType
) {
}
