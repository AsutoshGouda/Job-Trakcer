package com.jobtracker.careerflow.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateApplicationStatusDTO(
        @NotBlank(message = "Status cannot be blank")
        @Pattern(
                regexp = "APPLIED|INTERVIEW|OFFERED|REJECTED|WITHDRAW",
                message = "Invalid application status"
        )
        String status
) {
}
