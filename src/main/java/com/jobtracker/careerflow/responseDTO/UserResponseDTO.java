package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {
    String firstName;
    String lastName;
    long phoneNo;
    String email;
    String address;
}
