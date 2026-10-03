package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.NoteRequestDTO;
import com.jobtracker.careerflow.requestDTO.UpdateNoteRequestDTO;
import com.jobtracker.careerflow.responseDTO.NoteResponseDTO;
import com.jobtracker.careerflow.security.CustomerUserDetails;
import com.jobtracker.careerflow.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteResponseDTO> getAllMyNotes(@AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return noteService.getAllMyNotes(userId);
    }

    @GetMapping("/id/{id}")
    public NoteResponseDTO getNoteByNoteId(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return noteService.getNoteByNoteId(userId, id);
    }

    @GetMapping("/id/application/{id}")
    public List<NoteResponseDTO> getNoteByApplicationId(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return noteService.getNotesByApplicationId(userId, id);
    }

    @PostMapping
    public NoteResponseDTO addNote(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody NoteRequestDTO noteRequestDTO){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return noteService.save(userId, noteRequestDTO);
    }

    @PatchMapping("/updatenote/id/{id}")
    public NoteResponseDTO updateNote(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id,
                                      @Valid @RequestBody UpdateNoteRequestDTO updateNoteRequestDTO){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return noteService.updateNote(userId, id, updateNoteRequestDTO);
    }

    @DeleteMapping("/deletenote/id/{id}")
    public void deleteNote(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        noteService.deleteNote(userId, id);
    }

}
