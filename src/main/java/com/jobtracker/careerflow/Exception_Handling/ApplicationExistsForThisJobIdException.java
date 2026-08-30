package com.jobtracker.careerflow.Exception_Handling;

public class ApplicationExistsForThisJobIdException extends RuntimeException {
    public ApplicationExistsForThisJobIdException(String message) {
        super(message);
    }
}
