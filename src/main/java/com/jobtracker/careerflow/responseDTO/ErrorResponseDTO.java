package com.jobtracker.careerflow.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ErrorResponseDTO {

    OffsetDateTime timestamp;
    int status;
    String message;


    public ErrorResponseDTO(OffsetDateTime now, int i, String message) {
    }
}
