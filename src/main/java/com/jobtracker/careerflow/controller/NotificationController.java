package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.NotificationRequestDTO;
import com.jobtracker.careerflow.responseDTO.NotificationResponseDTO;
import com.jobtracker.careerflow.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationResponseDTO> getAllNotifications(){
        return notificationService.getAllNotifications();
    }

    @GetMapping("/id/{id}")
    public NotificationResponseDTO getNotificationById(@PathVariable UUID id){
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/user/{id}")
    public List<NotificationResponseDTO> getNotificationByUserId(@PathVariable UUID id){
        return notificationService.getNotificationsByUserId(id);
    }

    @GetMapping("/user/{id}/unread")
    public List<NotificationResponseDTO> getUnReadNotificationByUserId(@PathVariable UUID id){
        return notificationService.getUnreadNotificationsByUserId(id);
    }

    @PostMapping
    public NotificationResponseDTO addNotification(@Valid @RequestBody NotificationRequestDTO notificationRequestDTO){
        return notificationService.save(notificationRequestDTO);
    }

    @PatchMapping("/id/{id}/read")
    public NotificationResponseDTO readNotifications(@PathVariable UUID id){
        return notificationService.markAsRead(id);
    }

    @DeleteMapping("/id/{id}")
    public void deleteNotifications(@PathVariable UUID id){
        notificationService.deleteNotification(id);
    }

}
