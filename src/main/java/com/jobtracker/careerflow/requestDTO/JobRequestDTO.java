package com.jobtracker.careerflow.requestDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;
import java.util.UUID;

public record JobRequestDTO(
        UUID companyId,

        @NotBlank(message = "Job title cannot be empty!")
        String title,
        OffsetDateTime postedAt,
        String url
) {
}
