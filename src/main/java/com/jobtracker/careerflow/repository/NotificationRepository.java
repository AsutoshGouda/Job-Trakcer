package com.jobtracker.careerflow.repository;


import com.jobtracker.careerflow.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

    List<NotificationEntity> findByUserEntity_UserId(UUID id);

    List<NotificationEntity> findByUserEntity_UserIdAndIsReadFalse(UUID id);

}
