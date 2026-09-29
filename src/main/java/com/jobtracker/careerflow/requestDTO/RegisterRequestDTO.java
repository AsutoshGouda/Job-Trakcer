package com.jobtracker.careerflow.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
    @NotBlank
    String firstName,

    @NotBlank
    String lastName,

    @Email
    @NotBlank
    String email,

    @NotBlank
    String phoneNo,

    String address,

    @NotBlank
    String password
) {
}
