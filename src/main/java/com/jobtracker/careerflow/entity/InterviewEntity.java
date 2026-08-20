package com.jobtracker.careerflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID interviewId;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private ApplicationEntity applicationEntity;

    @Column(name = "scheduled", nullable = false)
    private OffsetDateTime scheduledAt;

    @Column(name = "interview_mode", nullable = false)
    private String interviewMode;

    @Column(name = "round_no", nullable = false)
    private int roundNo;

    @Column(name = "round_type", nullable = false)
    private String roundType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
