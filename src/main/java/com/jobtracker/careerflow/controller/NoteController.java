package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.NoteRequestDTO;
import com.jobtracker.careerflow.requestDTO.UpdateNoteRequestDTO;
import com.jobtracker.careerflow.responseDTO.NoteResponseDTO;
import com.jobtracker.careerflow.service.NoteService;
import jakarta.validation.Valid;
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
    public List<NoteResponseDTO> getAllNotes(){
        return noteService.getAllNotes();
    }

    @GetMapping("/id/{id}")
    public NoteResponseDTO getNoteByNoteId(@PathVariable UUID id){
        return noteService.getNoteByNoteId(id);
    }

    @GetMapping("/id/application/{id}")
    public List<NoteResponseDTO> getNoteByApplicationId(@PathVariable UUID id){
        return noteService.getNotesByApplicationId(id);
    }

    @PostMapping
    public NoteResponseDTO addNote(@Valid @RequestBody NoteRequestDTO noteRequestDTO){
        return noteService.save(noteRequestDTO);
    }

    @PatchMapping("/updatenote/id/{id}")
    public NoteResponseDTO updateNote(@PathVariable UUID id,
                                      @Valid @RequestBody UpdateNoteRequestDTO updateNoteRequestDTO){
        return noteService.updateNote(id, updateNoteRequestDTO);
    }

    @DeleteMapping("/deletenote/id/{id}")
    public void deleteNote(@PathVariable UUID id){
        noteService.deleteNote(id);
    }

}
