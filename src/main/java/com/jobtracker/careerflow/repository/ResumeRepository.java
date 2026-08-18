package com.jobtracker.careerflow.repository;

import com.jobtracker.careerflow.entity.ResumeEntity;
import com.jobtracker.careerflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResumeRepository extends JpaRepository<ResumeEntity, UUID> {
    List<ResumeEntity> findByUserEntity_UserId(UUID userId);

    Optional<ResumeEntity> findFirstByUserEntity_UserIdOrderByVersionDesc(UUID userId);

    default long getVersionForUser(UserEntity userEntity){
        return findFirstByUserEntity_UserIdOrderByVersionDesc(userEntity.getUserId()).map(ResumeEntity::getVersion).orElse(0L);
    }
}
