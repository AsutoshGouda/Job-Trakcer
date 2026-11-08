package com.jobtracker.careerflow.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record NotificationRequestDTO(
        @NotNull(message = "Notification type cannot be null")
        @Pattern(regexp = "^(INTERVIEW_REMINDER|APPLICATION_UPDATE|FOLLOW_UP|SYSTEM)$", message = "Invalid Notification type")
        String type,
        @NotNull(message = "Notification channel cannot be null")
        @Pattern(regexp = "^(IN_APP|EMAIL)$", message = "Invalid Notification channel")
        String channel,
        @NotBlank(message = "Notification message cannot be blank")
        String message
) {
}
