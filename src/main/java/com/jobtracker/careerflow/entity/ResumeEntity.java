package com.jobtracker.careerflow.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ResumeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "resume_id")
    private UUID resumeId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UUID userId;

    @Column(name = "url")
    private String path;

    @Column(name = "version")
    private long version;

    @Column(name = "uploaded_at")
    private OffsetDateTime uploadedAt;
}
