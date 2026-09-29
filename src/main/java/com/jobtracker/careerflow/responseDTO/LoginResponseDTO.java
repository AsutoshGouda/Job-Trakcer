package com.jobtracker.careerflow.responseDTO;

import lombok.Getter;

@Getter
public class LoginResponseDTO {
    String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
