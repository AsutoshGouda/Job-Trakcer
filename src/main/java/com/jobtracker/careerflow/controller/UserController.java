package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.UserRepository;
import com.jobtracker.careerflow.responseDTO.UserResponseDTO;
import com.jobtracker.careerflow.resquestDTO.UserRequestDTO;
import com.jobtracker.careerflow.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserByUserId(@PathVariable UUID id){
        return userService.getUserById(id);
    }

    @PostMapping("/createUser/")
    public UserRequestDTO postUser(@RequestBody UserRequestDTO userRequestDTO){
        return userService.save(userRequestDTO);
    }
}
