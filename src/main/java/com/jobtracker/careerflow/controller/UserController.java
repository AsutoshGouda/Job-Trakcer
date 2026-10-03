package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.responseDTO.UserResponseDTO;
import com.jobtracker.careerflow.requestDTO.UserRequestDTO;
import com.jobtracker.careerflow.security.CustomerUserDetails;
import com.jobtracker.careerflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponseDTO getMyProfile(@AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return userService.getUser(userId);
    }

    @PatchMapping("/updateMyDetails")
    public UserResponseDTO updateMyDetails(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UserRequestDTO userRequestDTO){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return userService.updateUser(userId, userRequestDTO);
    }

    @DeleteMapping("/deleteMe")
    public UserResponseDTO deleteMe(@AuthenticationPrincipal UserDetails userDetails){
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetails;
        UUID userId = customerUserDetails.getUserId();
        return userService.deleteMe(userId);
    }
}
