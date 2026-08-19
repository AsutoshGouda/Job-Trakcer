package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class JobResponseDTO {
    UUID jobId;
    UUID companyId;
    String title;
    OffsetDateTime postedAt;
    String status;
    String url;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
