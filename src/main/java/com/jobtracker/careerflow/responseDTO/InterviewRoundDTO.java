package com.jobtracker.careerflow.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InterviewRoundDTO {
    private String roundType;
    private int roundNo;
    private long count;
}
