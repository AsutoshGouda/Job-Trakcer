package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.ApplicationNotFoundException;
import com.jobtracker.careerflow.Exception_Handling.NoteNotFoundException;
import com.jobtracker.careerflow.entity.ApplicationEntity;
import com.jobtracker.careerflow.entity.NoteEntity;
import com.jobtracker.careerflow.repository.ApplicationRepository;
import com.jobtracker.careerflow.repository.NoteRepository;
import com.jobtracker.careerflow.requestDTO.NoteRequestDTO;
import com.jobtracker.careerflow.requestDTO.UpdateNoteRequestDTO;
import com.jobtracker.careerflow.responseDTO.NoteResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final ApplicationRepository applicationRepository;

    public NoteService(NoteRepository noteRepository, ApplicationRepository applicationRepository){
        this.noteRepository = noteRepository;
        this.applicationRepository = applicationRepository;
    }

    public NoteResponseDTO mapToResponse(NoteEntity noteEntity){
        return new NoteResponseDTO(
                noteEntity.getNoteId(),
                noteEntity.getApplicationEntity().getApplicationId(),
                noteEntity.getContent(),
                noteEntity.getCreatedAt(),
                noteEntity.getUpdatedAt()
        );
    }

    public NoteResponseDTO save(NoteRequestDTO noteRequestDTO){
        ApplicationEntity applicationEntity =
                applicationRepository.findById(noteRequestDTO.applicationId()).orElseThrow(()->new ApplicationNotFoundException("Application Not Found!"));

        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setApplicationEntity(applicationEntity);
        noteEntity.setContent(noteRequestDTO.content());
        noteRepository.save(noteEntity);
        return mapToResponse(noteEntity);
    }

    public List<NoteResponseDTO> getAllNotes(){
        return noteRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public NoteResponseDTO getNoteByNoteId(UUID noteId){
        NoteEntity noteEntity = noteRepository.findById(noteId).orElseThrow(()->new NoteNotFoundException("Note Not Found!"));
        return mapToResponse(noteEntity);
    }

    public List<NoteResponseDTO> getNotesByApplicationId(UUID applicationId){
        ApplicationEntity applicationEntity =
                applicationRepository.findById(applicationId).orElseThrow(()-> new ApplicationNotFoundException(
                        "Application Doesn't Exist!"));
        List<NoteEntity> noteEntities = noteRepository.findByApplicationEntity_ApplicationId(applicationId);
        if(noteEntities.isEmpty()){
            throw new NoteNotFoundException("Application Found but has no notes!");
        }
        return noteEntities.stream().map(this::mapToResponse).toList();
    }

    public NoteResponseDTO updateNote(UUID noteId, UpdateNoteRequestDTO updateNoteRequestDTO){
        NoteEntity noteEntity =
                noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note Not Found!"));

        if (updateNoteRequestDTO.content() != null && !updateNoteRequestDTO.content().isEmpty()) {
            noteEntity.setContent(updateNoteRequestDTO.content());
        }

        noteRepository.save(noteEntity);
        return mapToResponse(noteEntity);
    }

    public void deleteNote(UUID noteId){
        NoteEntity noteEntity = noteRepository.findById(noteId).orElseThrow(()->new NoteNotFoundException("Note Not Found!"));
        noteRepository.delete(noteEntity);
    }
}
