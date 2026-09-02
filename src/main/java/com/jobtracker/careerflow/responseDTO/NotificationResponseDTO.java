package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class NotificationResponseDTO {
    UUID notificationId;
    UUID userId;
    String message;
    String type;
    String channel;
    boolean isRead;
    OffsetDateTime createdAt;
}
