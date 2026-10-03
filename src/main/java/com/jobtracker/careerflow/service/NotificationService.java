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

    public NotificationResponseDTO save(UUID userId, NotificationRequestDTO notificationRequestDTO){
        UserEntity userEntity =
                userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(
                        "User Not Found!"));
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setUserEntity(userEntity);
        notificationEntity.setType(notificationRequestDTO.type());
        notificationEntity.setChannel(notificationRequestDTO.channel());
        notificationEntity.setMessage(notificationRequestDTO.message());

        notificationRepository.save(notificationEntity);
        return mapToResponse(notificationEntity);
    }

    public List<NotificationResponseDTO> getAllMyNotifications(UUID userId){
        return notificationRepository.findByUserEntity_UserId(userId).stream().map(this::mapToResponse).toList();
    }

    public NotificationResponseDTO getNotificationById(UUID userId, UUID id){
        NotificationEntity notificationEntity =
                notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("Notification Not Found!"));
        if(!userId.equals(notificationEntity.getUserEntity().getUserId())){
            throw new NotificationNotFoundException("Notification Not Found!");
        }
        return mapToResponse(notificationEntity);
    }

    public List<NotificationResponseDTO> getUnreadNotificationsByUserId(UUID userId){
        userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User Not Found!"));
        List<NotificationEntity> notificationEntities =
                notificationRepository.findByUserEntity_UserIdAndIsReadFalse(userId);
        return notificationEntities.stream().map(this::mapToResponse).toList();
    }

    public NotificationResponseDTO markAsRead(UUID userId, UUID id){
        NotificationEntity notificationEntity =
                notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("Notification Not Found!"));
        if(!userId.equals(notificationEntity.getUserEntity().getUserId())){
            throw new NotificationNotFoundException("Notification Not Found!");
        }
        notificationEntity.setRead(true);
        notificationRepository.save(notificationEntity);
        return mapToResponse(notificationEntity);
    }

    public void deleteNotification(UUID userId, UUID id){
        NotificationEntity notificationEntity =
                notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("Notification Not Found!"));
        if(!userId.equals(notificationEntity.getUserEntity().getUserId())){
            throw new NotificationNotFoundException("Notification Not Found!");
        }
        notificationRepository.delete(notificationEntity);
    }

}
