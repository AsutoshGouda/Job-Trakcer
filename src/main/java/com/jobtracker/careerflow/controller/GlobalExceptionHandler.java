package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.Exception_Handling.*;
import com.jobtracker.careerflow.responseDTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ApplicationNotFoundException.class, CompanyNotFoundException.class,
            InterviewNotFoundException.class, JobNotFoundException.class, NoteNotFoundException.class,
            NotificationNotFoundException.class, ResumeNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleNotFoundExceptions(Exception ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                OffsetDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ApplicationAlreadyExistsException.class, ApplicationExistsForThisJobIdException.class,
            ApplicationHasInterviewsException.class, InterviewExistsException.class, ResumeAlreadyUsedException.class})
    public ResponseEntity<ErrorResponseDTO> handleConflictExceptions(Exception ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                OffsetDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResumeOwnershipException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbiddenExceptions(Exception ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                OffsetDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestExceptions(MethodArgumentNotValidException  ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult().getFieldErrors().toString()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
