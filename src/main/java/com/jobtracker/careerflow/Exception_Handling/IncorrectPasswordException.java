package com.jobtracker.careerflow.Exception_Handling;

public class IncorrectPasswordException extends RuntimeException {
  public IncorrectPasswordException(String message) {
    super(message);
  }
}
