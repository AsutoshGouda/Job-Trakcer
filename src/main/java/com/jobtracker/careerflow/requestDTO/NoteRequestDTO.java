package com.jobtracker.careerflow.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NoteRequestDTO(
        @NotNull(message = "ApplicationId cannot be null")
        UUID applicationId,

        @NotBlank(message = "Note Content cannot be blank")
        String content
) {
}
