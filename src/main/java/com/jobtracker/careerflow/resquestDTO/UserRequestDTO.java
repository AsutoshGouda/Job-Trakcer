package com.jobtracker.careerflow.resquestDTO;

import java.lang.String;

public record UserRequestDTO(
    String firstName,
    String lastName,
    String email,
    long phoneNo,
    String address
){}
