package com.jobtracker.careerflow.requestDTO;

import jakarta.validation.constraints.NotBlank;

public record UpdateNoteRequestDTO(
        @NotBlank(message = "Note Content Cannot be empty")
        String content
) {
}

