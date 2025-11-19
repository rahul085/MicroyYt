package com.example.auth_service.Controller;

import com.example.auth_service.entity.Users;
import com.example.auth_service.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService service;

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

}