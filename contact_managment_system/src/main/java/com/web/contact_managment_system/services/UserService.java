package com.web.contact_managment_system.services;

import com.web.contact_managment_system.models.User;
import com.web.contact_managment_system.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    public void save(User user){
        userRepository.save(user);
    }
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Optional<User> findByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
}
