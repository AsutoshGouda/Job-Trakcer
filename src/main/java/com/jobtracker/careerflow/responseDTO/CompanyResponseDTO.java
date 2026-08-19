package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CompanyResponseDTO {
    UUID companyId;
    String companyName;
    String website;
    String industry;
    String location;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
