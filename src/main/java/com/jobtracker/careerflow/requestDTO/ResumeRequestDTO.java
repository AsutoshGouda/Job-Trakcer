package com.jobtracker.careerflow.requestDTO;

import java.util.UUID;

public record ResumeRequestDTO(
        UUID userId,
        String url
) {
}
