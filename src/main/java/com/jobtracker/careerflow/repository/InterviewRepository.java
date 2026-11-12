package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.ApplicationEntity;
import com.jobtracker.careerflow.entity.InterviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface InterviewRepository extends JpaRepository<InterviewEntity, UUID> {

    List<InterviewEntity> findByApplicationEntity_ApplicationIdAndApplicationEntity_UserEntity_UserId(UUID id, UUID userId);
    boolean existsByApplicationEntity(ApplicationEntity applicationEntity);
    boolean existsByApplicationEntityAndRoundNo(
            ApplicationEntity applicationEntity,
            int roundNo
    );
    boolean existsByApplicationEntityAndRoundNoAndInterviewIdNot(
            ApplicationEntity applicationEntity,
            int roundNo,
            UUID interviewId
    );

    //DASHBOARD APIs
    long countByApplicationEntity_UserEntity_UserId(UUID userId);
    long countByApplicationEntity_UserEntity_UserIdAndScheduledAtAfter(
            UUID userId,
            OffsetDateTime now
    );

    @Query("""
        SELECT
            i.roundType AS roundType,
            i.roundNo AS roundNo,
            COUNT(i) AS count
        FROM InterviewEntity i
        WHERE i.applicationEntity.userEntity.userId = :userId
        GROUP BY i.roundType, i.roundNo
        ORDER BY i.roundNo
    """)
    List<InterviewRoundProjection> findInterviewRoundStats(
            @Param("userId")UUID userId
    );
}
