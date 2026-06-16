package com.jobtracker.careerflow.controller;

import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.UserRepository;
import com.jobtracker.careerflow.responseDTO.UserResponseDTO;
import com.jobtracker.careerflow.resquestDTO.UserRequestDTO;
import com.jobtracker.careerflow.service.UserService;
import jakarta.validation.Valid;
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

    @GetMapping("/id/{id}")
    public UserResponseDTO getUserByUserId(@PathVariable UUID id){
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public UserResponseDTO getUserbyEmail(@PathVariable String email){
        return userService.getUsersByEmail(email);
    }

    @GetMapping("/phoneno/{phoneno}")
    public UserResponseDTO getUserbyPhoneno(@PathVariable long phoneno){
        return userService.getUserByPhoneNo(phoneno);
    }

    @PostMapping("/createUser")
    public UserResponseDTO postUser(@Valid @RequestBody CreateUserRequestDTO userRequestDTO){
        return userService.save(userRequestDTO);
    }

    @PatchMapping("/updateUser/email/{email}")
    public UserResponseDTO updateUser_email(@PathVariable String email, @Valid @RequestBody UserRequestDTO userRequestDTO){
        return userService.updateUser_email(email, userRequestDTO);
    }

    @PatchMapping("/updateUser/phoneno/{phoneno}")
    public UserResponseDTO updateUser_phoneno(@PathVariable long phoneno, @Valid @RequestBody UserRequestDTO userRequestDTO){
        return userService.updateUser_phoneno(phoneno, userRequestDTO);
    }

    @DeleteMapping("/deleteUser/phoneno/{phoneno}")
    public UserResponseDTO deleteUser_phone(@PathVariable long phoneno){
        return userService.deleteUser_phone(phoneno);
    }

    @DeleteMapping("/deleteUser/email/{email}")
    public UserResponseDTO deleteUser_email(@PathVariable String email){
        return userService.deleteUser_email(email);
    }
}
