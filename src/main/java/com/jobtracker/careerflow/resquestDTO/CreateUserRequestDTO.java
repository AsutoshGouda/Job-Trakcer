package com.jobtracker.careerflow.resquestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDTO(
        @NotBlank(message = "First Name cannot be empty.")
        String firstName,

        @NotBlank(message = "Last Name cannot be empty.")
        String lastName,

        @Email @NotBlank(message = "Email cannot be empty.")
        String email,

        @NotBlank(message = "Phone No. cannot be empty.")
        long phoneNo,

        @NotBlank(message = "Address cannot be empty.")
        String address
) {
}
