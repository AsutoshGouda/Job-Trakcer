package com.jobtracker.careerflow.requestDTO;

import java.lang.String;

public record UserRequestDTO(
    String firstName,
    String lastName,
    String email,
    String phoneNo,
    String address
){}
