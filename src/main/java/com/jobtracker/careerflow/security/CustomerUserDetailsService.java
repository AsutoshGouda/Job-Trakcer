package com.jobtracker.careerflow.security;

import com.jobtracker.careerflow.Exception_Handling.UserNotFoundException;
import com.jobtracker.careerflow.entity.UserEntity;
import com.jobtracker.careerflow.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomerUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UserNotFoundException {
        UserEntity userEntity = userRepository.getUserByEmail(userName).orElseThrow(()->new UserNotFoundException("User Not Found!!"));

        return new CustomerUserDetails(userEntity);

    }

}
