package com.jobtracker.careerflow.requestDTO;

import java.util.UUID;

public record NotificationRequestDTO(
        UUID userId,
        String type,
        String channel,
        String message
) {
}
