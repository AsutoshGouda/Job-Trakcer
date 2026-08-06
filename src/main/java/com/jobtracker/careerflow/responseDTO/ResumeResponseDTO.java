package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class ResumeResponseDTO{
    String firstName;
    String lastName;
    String fileUrl;
    int version;
    OffsetDateTime uploadedAt;
}
