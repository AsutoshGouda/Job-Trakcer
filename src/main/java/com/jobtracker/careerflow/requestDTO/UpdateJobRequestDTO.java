package com.jobtracker.careerflow.requestDTO;

import java.util.UUID;

public record UpdateJobRequestDTO(
        UUID companyId,
        String companyNameOther,
        String status,
        String title,
        String url
) {
}
