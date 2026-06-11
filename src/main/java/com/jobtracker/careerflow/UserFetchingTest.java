package com.jobtracker.careerflow;

import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFetchingTest implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args){
        System.out.println("Users present = " + userRepository.count());
    }
}
