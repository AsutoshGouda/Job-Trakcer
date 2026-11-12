package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, UUID> {

    List<ApplicationEntity> findByUserEntity_UserId(UUID id);
    List<ApplicationEntity> findByJobEntity_JobIdAndUserEntity_UserId(UUID jobId, UUID userId);
    boolean existsByUserEntity_UserIdAndJobEntity_JobId(UUID userId, UUID jobId);
    boolean existsByResumeEntity_ResumeId(UUID resumeId);
    boolean existsByJobEntity_JobId(UUID jobId);

    //DASHBOARD APIs
    long countByUserEntity_UserId(UUID userId);

    @Query("""
            SELECT a.status AS status, COUNT(a) AS count FROM ApplicationEntity a WHERE a.userEntity.userId = :userId
            GROUP BY a.status
            """)
    List<ApplicationStatusProjection> countApplicationsByStatus(@Param("userId")UUID userId);

    @Query("""
            SELECT 
                c.companyId AS companyId,
                COALESCE(c.companyName, j.companyNameOther) AS companyName,
                COUNT(a) AS totalApplications
            FROM ApplicationEntity a
            JOIN a.jobEntity j
            LEFT JOIN j.companyEntity c
            WHERE a.userEntity.userId = :userId
            GROUP BY c.companyId, COALESCE(c.companyName, j.companyNameOther)
            """)
    List<CompanyApplicationProjection> findCompanyApplicationStats(
            @Param("userId") UUID userId
    );

    @Query("""
        SELECT
            r.resumeId AS resumeId,
            r.version AS version,
            COUNT(a) AS totalApplications,
            COUNT(DISTINCT 
                CASE
                    WHEN c.companyId IS NOT NULL THEN CONCAT('ID:', CAST(c.companyId AS String))
                    ELSE CONCAT('NAME:', j.companyNameOther)
                END
            ) AS totalCompanies
        FROM ApplicationEntity a
        JOIN a.resumeEntity r
        JOIN a.jobEntity j
        LEFT JOIN j.companyEntity c
        WHERE a.userEntity.userId = :userId
        GROUP BY r.resumeId, r.version
    """)
    List<ResumeApplicationProjection> findResumeApplicationStats(
            @Param("userId") UUID userId
    );

    @Query("""
        SELECT COUNT(DISTINCT
            CASE
                WHEN c.companyId IS NOT NULL
                    THEN CONCAT('ID:', CAST(c.companyId AS String))
                ELSE CONCAT('NAME', j.companyNameOther)
            END
        ) 
        FROM ApplicationEntity a
        JOIN a.jobEntity j
        LEFT JOIN j.companyEntity c
        WHERE a.userEntity.userId = :userId
    """)
    long countCompanies(@Param("userId") UUID userId);
}
