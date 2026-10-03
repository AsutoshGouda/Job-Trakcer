package com.jobtracker.careerflow.requestDTO;


import java.time.OffsetDateTime;
import java.util.UUID;

public record ApplicationRequestDTO (
        UUID resumeId,
        UUID jobId,
        OffsetDateTime appliedAt
){
}