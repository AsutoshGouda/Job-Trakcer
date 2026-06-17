package com.jobtracker.careerflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "resume")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "resume_id")
    private UUID resumeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "url")
    private String path;

    @Column(name = "version")
    private long version;

    @Column(name = "uploaded_at")
    private OffsetDateTime uploadedAt;

    @PrePersist
    public void onUpload(){
        this.uploadedAt = OffsetDateTime.now();
    }
}
