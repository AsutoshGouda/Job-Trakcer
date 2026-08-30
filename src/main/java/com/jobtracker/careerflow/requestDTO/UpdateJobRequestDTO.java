package com.jobtracker.careerflow.requestDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateJobRequestDTO(
        String title,
        String url
) {
}
