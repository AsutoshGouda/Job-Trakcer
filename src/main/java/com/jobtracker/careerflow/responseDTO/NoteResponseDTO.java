package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class NoteResponseDTO {
    UUID noteId;
    UUID applicationId;
    String content;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
