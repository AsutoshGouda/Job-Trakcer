package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {

    List<NoteEntity> findByApplicationEntity_ApplicationId(UUID applicationId);

}
