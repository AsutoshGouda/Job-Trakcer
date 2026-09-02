package com.jobtracker.careerflow.service;

import com.jobtracker.careerflow.Exception_Handling.NotificationNotFoundException;
import com.jobtracker.careerflow.Exception_Handling.UserNotFoundException;
import com.jobtracker.careerflow.entity.NotificationEntity;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.NotificationRepository;
import com.jobtracker.careerflow.repository.UserRepository;
import com.jobtracker.careerflow.requestDTO.NotificationRequestDTO;
import com.jobtracker.careerflow.responseDTO.NotificationResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository){
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public NotificationResponseDTO mapToResponse(NotificationEntity notificationEntity){
        return new NotificationResponseDTO(
                notificationEntity.getNotificationId(),
                notificationEntity.getUserEntity().getUserId(),
                notificationEntity.getType(),
                notificationEntity.getChannel(),
                notificationEntity.getMessage(),
                notificationEntity.isRead(),
                notificationEntity.getCreatedAt()
        );
    }

    public NotificationResponseDTO save(NotificationRequestDTO notificationRequestDTO){
        UserEntity userEntity =
                userRepository.findById(notificationRequestDTO.userId()).orElseThrow(()->new UserNotFoundException(
                        "User Not Found!"));
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setUserEntity(userEntity);
        notificationEntity.setType(notificationRequestDTO.type());
        notificationEntity.setChannel(notificationRequestDTO.channel());
        notificationEntity.setMessage(notificationRequestDTO.message());

        notificationRepository.save(notificationEntity);
        return mapToResponse(notificationEntity);
    }

    public List<NotificationResponseDTO> getAllNotifications(){
        return notificationRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public NotificationResponseDTO getNotificationById(UUID id){
        NotificationEntity notificationEntity =
                notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("Notification Not Found!"));
        return mapToResponse(notificationEntity);
    }

    public List<NotificationResponseDTO> getNotificationsByUserId(UUID id){
        userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found!"));
        List<NotificationEntity> notificationEntities =
                notificationRepository.findByUserEntity_UserId(id);
        return notificationEntities.stream().map(this::mapToResponse).toList();
    }

    public List<NotificationResponseDTO> getUnreadNotificationsByUserId(UUID id){
        userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found!"));
        List<NotificationEntity> notificationEntities =
                notificationRepository.findByUserEntity_UserIdAndIsReadFalse(id);
        return notificationEntities.stream().map(this::mapToResponse).toList();
    }

    public NotificationResponseDTO markAsRead(UUID id){
        NotificationEntity notificationEntity =
                notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("Notification Not Found!"));
        notificationEntity.setRead(true);
        notificationRepository.save(notificationEntity);
        return mapToResponse(notificationEntity);
    }

    public void deleteNotification(UUID id){
        NotificationEntity notificationEntity =
                notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("Notification Not Found!"));
        notificationRepository.delete(notificationEntity);
    }

}
