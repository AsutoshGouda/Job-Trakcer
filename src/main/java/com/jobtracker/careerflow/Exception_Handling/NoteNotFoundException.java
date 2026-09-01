package com.jobtracker.careerflow.Exception_Handling;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
