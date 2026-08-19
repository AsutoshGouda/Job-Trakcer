package com.jobtracker.careerflow.Exception_Handling;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(String message) {
        super(message);
    }
}
