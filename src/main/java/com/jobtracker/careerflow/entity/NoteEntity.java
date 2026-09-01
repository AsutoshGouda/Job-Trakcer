package com.jobtracker.careerflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID noteId;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private ApplicationEntity applicationEntity;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate(){
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    void onUpdate(){
        this.updatedAt = OffsetDateTime.now();
    }
}
