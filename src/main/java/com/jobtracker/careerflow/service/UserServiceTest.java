package com.jobtracker.careerflow.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserServiceTest implements CommandLineRunner {

    private final UserService userService;

    public UserServiceTest(UserService userService){
        this.userService = userService;
    }

    @Override
    public void run(String... args){
        //System.out.println("Total users = "+ userService.getAllUsers().size());
    }
}
