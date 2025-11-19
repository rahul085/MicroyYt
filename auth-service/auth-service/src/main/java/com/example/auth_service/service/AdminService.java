package com.example.auth_service.service;

import com.example.auth_service.entity.Users;
import com.example.auth_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }
}