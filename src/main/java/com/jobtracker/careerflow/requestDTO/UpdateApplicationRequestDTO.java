package com.jobtracker.careerflow.requestDTO;

import java.time.OffsetDateTime;

public record UpdateApplicationRequestDTO(
        OffsetDateTime appliedAt
) {
}
