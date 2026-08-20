package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class InterviewResponseDTO {
    UUID interviewId;
    UUID applicationId;
    OffsetDateTime scheduledAt;
    String interviewMode;
    int roundNo;
    String roundType;
    String status;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
