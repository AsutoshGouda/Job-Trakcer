package com.jobtracker.careerflow.requestDTO;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequestDTO (
        @NotBlank(message = "Company Name cannot be empty.")
        String companyName,

        String website,
        String industry,
        String location
){
}
