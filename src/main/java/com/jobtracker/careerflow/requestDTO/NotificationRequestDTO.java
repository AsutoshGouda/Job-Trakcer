package com.jobtracker.careerflow.requestDTO;

import java.util.UUID;

public record NotificationRequestDTO(
        String type,
        String channel,
        String message
) {
}
