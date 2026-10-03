package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.requestDTO.NotificationRequestDTO;
import com.jobtracker.careerflow.responseDTO.NotificationResponseDTO;
import com.jobtracker.careerflow.security.CustomerUserDetails;
import com.jobtracker.careerflow.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public List<NotificationResponseDTO> getAllMyNotifications(@AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return notificationService.getAllMyNotifications(userId);
    }

    @GetMapping("/id/{id}")
    public NotificationResponseDTO getNotificationById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return notificationService.getNotificationById(userId, id);
    }

    @GetMapping("/unread")
    public List<NotificationResponseDTO> getUnReadNotifications(@AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return notificationService.getUnreadNotificationsByUserId(userId);
    }

    @PostMapping
    public NotificationResponseDTO addNotification(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody NotificationRequestDTO notificationRequestDTO){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return notificationService.save(userId, notificationRequestDTO);
    }

    @PatchMapping("/id/{id}/read")
    public NotificationResponseDTO markNotificationsAsRead(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return notificationService.markAsRead(userId, id);
    }

    @DeleteMapping("/id/{id}")
    public void deleteNotifications(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        notificationService.deleteNotification(userId, id);
    }

}
