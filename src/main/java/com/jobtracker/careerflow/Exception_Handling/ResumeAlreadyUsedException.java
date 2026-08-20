package com.jobtracker.careerflow.Exception_Handling;

public class ResumeAlreadyUsedException extends RuntimeException {
    public ResumeAlreadyUsedException(String message) {
        super(message);
    }
}
