package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ApplicationResponseDTO {
    UUID resumeId;
    UUID jobId;
    UUID userId;
    String status;
    OffsetDateTime appliedAt;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
