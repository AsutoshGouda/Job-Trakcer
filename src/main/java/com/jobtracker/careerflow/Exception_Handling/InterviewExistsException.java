package com.jobtracker.careerflow.Exception_Handling;

public class InterviewExistsException extends RuntimeException {
    public InterviewExistsException(String message) {
        super(message);
    }
}
