package com.jobtracker.careerflow.requestDTO;

import java.util.UUID;

public record NoteRequestDTO(
        UUID applicationId,
        String content
) {
}
