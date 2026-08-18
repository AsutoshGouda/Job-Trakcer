package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResumeResponseDTO {
    UUID resumeId;
    String url;
    long version;
    OffsetDateTime uploadedAt;
}
